package kmp.shared.todo.data.source

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksLocalSource {
    suspend fun getAllTasks(): Result<List<Task>>
    fun observerAllTasks(): Flow<List<Task>>
    suspend fun saveAllTasks(value: List<Task>)
    suspend fun deleteAllTasks()
}