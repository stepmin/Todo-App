package kmp.shared.todo.infrastructure.db

import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.base.infrastructure.db.AppDatabase
import kmp.shared.base.infrastructure.db.entities.TaskDetailEntity
import kmp.shared.base.infrastructure.db.entities.TaskEntity
import kmp.shared.todo.data.source.TasksLocalSource
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.model.TaskDetailRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TasksLocalSourceImpl(
    private val database: AppDatabase,
) : TasksLocalSource {
    override suspend fun getAllTasks(): Result<List<Task>> {
        return try {
            withContext(Dispatchers.IO) {
                Result.Success(
                    database.getTaskListDao().getAll().map {
                        it.toDomainModel()
                    },
                )
            }
        } catch (e: Exception) {
            //TODO-specify execption
            println("error when getting data from db: $e")
            Result.Error(CommonError.Unknown)
        }
    }

    override fun observerAllTasks(): Flow<List<Task>> {
        return database.getTaskListDao().observerTasks().map {
            it.map { it.toDomainModel() }
        }
    }

    override suspend fun saveAllTasks(value: List<Task>): Result<Boolean> {
        return try {
            val items = value.map {
                TaskEntity(it.id, it.userId, it.title, it.completed)
            }
            withContext(Dispatchers.IO) {
                database.getTaskListDao().insertAll(items)
            }
            Result.Success(true)
        } catch (e: Exception) {
            println("exception when saving tasks: $e")
            //TODO-specify exception
            Result.Error(CommonError.Unknown)
        }
    }

    override suspend fun saveOneTask(value: Task): Result<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                println("completed: ${value.completed}")
                database.getTaskListDao().insert(
                    TaskEntity(
                        id = value.id,
                        userId = value.userId,
                        title = value.title,
                        completed = value.completed,
                    )
                )
            }
            Result.Success(true)
        } catch (e: Exception) {
            println("exception when saving tasks: $e")
            Result.Error(CommonError.Unknown)
        }
    }

    override suspend fun deleteAllTasks() {
        database.getTaskListDao().deleteAll()
    }

    override suspend fun getTaskDetail(request: TaskDetailRequest): Result<TaskDetail> {
        return try {
            withContext(Dispatchers.IO) {
                println("detail data flow: task detail local request")
                val taskDetail = database.getTaskDetailDao().getTaskDetail(request.id, request.userId)
                println("detail data flow: task detail: $taskDetail")
                Result.Success(taskDetail.toDomain())
            }
        } catch (e: Exception) {
            println("exception when getting task detail e: $e")
            Result.Error(CommonError.Unknown)
        }
    }

    override suspend fun saveTaskDetail(detail: TaskDetail): Result<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                database.getTaskDetailDao().saveTaskDetail(
                    TaskDetailEntity(
                        id = detail.id,
                        userId = detail.userId,
                        title = detail.title,
                        completed = detail.completed,
                        name = detail.name,
                        userName = detail.username,
                        email = detail.email,
                    )
                )
            }
            Result.Success(true)
        } catch (e: Exception) {
            println("exception when saving task detail: $e")
            //TODO-specify execption
            Result.Error(CommonError.Unknown)
        }
    }
}

private fun TaskEntity.toDomainModel(): Task {
    return Task(id, userId, title, completed)
}

fun TaskDetailEntity.toDomain(): TaskDetail {
    return TaskDetail(
        id,
        userId,
        title,
        completed,
        name,
        userName,
        email,
    )
}
