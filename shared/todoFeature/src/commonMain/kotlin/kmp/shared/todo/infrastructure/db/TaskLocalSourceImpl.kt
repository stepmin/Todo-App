package kmp.shared.todo.infrastructure.db

import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.base.infrastructure.db.AppDatabase
import kmp.shared.base.infrastructure.db.entities.TaskEntity
import kmp.shared.todo.data.source.TasksLocalSource
import kmp.shared.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskLocalSourceImpl(
    private val database: AppDatabase,
) : TasksLocalSource {
    override suspend fun getAllTasks(): Result<List<Task>> {
        try {
            return Result.Success(
                database.getTodoDao().getAll().map {
                    it.toDomainModel()
                },
            )
        } catch (e: Exception) {
            //TODO-specify execption
            println("error when getting data from db: $e")
            return Result.Error(CommonError.Unknown)
        }
    }

    override fun observerAllTasks(): Flow<List<Task>> {
        return database.getTodoDao().observerTasks().map {
            it.map { it.toDomainModel() }
        }
    }

    override suspend fun saveAllTasks(value: List<Task>) {
        val items = value.map {
            TaskEntity(it.id, it.userId, it.title, it.completed)
        }
        println("items size: ${items.size}")
        database.getTodoDao().insertAll(items)
    }

    override suspend fun deleteAllTasks() {
        database.getTodoDao().deleteAll()
    }
}

private fun TaskEntity.toDomainModel(): Task {
    return Task(id, userId, title, completed)
}
