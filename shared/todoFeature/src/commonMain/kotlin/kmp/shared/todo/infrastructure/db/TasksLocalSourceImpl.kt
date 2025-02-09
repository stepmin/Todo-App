package kmp.shared.todo.infrastructure.db

import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.base.infrastructure.db.AppDatabase
import kmp.shared.base.infrastructure.db.entities.TaskEntity
import kmp.shared.todo.data.source.TasksLocalSource
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.usecase.TaskId
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
                    database.getTasksDao().getAll().map {
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
        return database.getTasksDao().observerTasks().map {
            it.map { it.toDomainModel() }
        }
    }

    override suspend fun saveAllTasks(value: List<Task>): Result<Boolean> {
        return try {
            val items = value.map {
                TaskEntity(it.id, it.userId, it.title, it.completed)
            }
            withContext(Dispatchers.IO) {
                database.getTasksDao().insertAll(items)
            }
            Result.Success(true)
        } catch (e: Exception) {
            println("exception when saving tasks: $e")
            //TODO-specify exception
            Result.Error(CommonError.Unknown)
        }
    }

    override suspend fun saveTask(detail: Task): Result<Boolean> {
        return try {
            withContext(Dispatchers.IO) {
                database.getTasksDao().saveTask(
                    TaskEntity(
                        id = detail.id,
                        userId = detail.userId,
                        title = detail.title,
                        completed = detail.completed,
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

    override suspend fun deleteAllTasks() {
        database.getTasksDao().deleteAll()
    }

    override suspend fun getTask(taskId: TaskId): Result<Task> {
        return try {
            withContext(Dispatchers.IO) {
                println("detail data flow: task detail local request")
                val taskDetail = database.getTasksDao().getTask(taskId)
                println("detail data flow: task detail: $taskDetail")
                Result.Success(taskDetail.toDomain())
            }
        } catch (e: Exception) {
            println("exception when getting task detail e: $e")
            Result.Error(CommonError.Unknown)
        }
    }
}

private fun TaskEntity.toDomainModel(): Task {
    return Task(id, userId, title, completed)
}

fun TaskEntity.toDomain(): Task {
    return Task(
        id,
        userId,
        title,
        completed,
    )
}
