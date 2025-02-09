package kmp.shared.todo.infrastructure.db

import kmp.shared.base.infrastructure.db.AppDatabase
import kmp.shared.base.infrastructure.db.entities.TaskEntity
import kmp.shared.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskLocalSource(
    private val database: AppDatabase,
) {
    suspend fun getAllTasks(): List<Task> {
        return database.getTodoDao().getAll().map {
            it.toDomainModel()
        }
    }

    fun observerAllTasks(): Flow<List<Task>> {
        return database.getTodoDao().observerTasks().map {
            it.map { it.toDomainModel() }
        }
    }

    suspend fun saveAllTasks(value: List<Task>) {
        val items = value.map {
            TaskEntity(it.id, it.userId, it.title, it.completed)
        }
        println("items size: ${items.size}")
        database.getTodoDao().insertAll(items)
    }

    suspend fun deleteAllTasks() {
        database.getTodoDao().deleteAll()
    }
}

private fun TaskEntity.toDomainModel(): Task {
    return Task(id, userId, title, completed)
}
