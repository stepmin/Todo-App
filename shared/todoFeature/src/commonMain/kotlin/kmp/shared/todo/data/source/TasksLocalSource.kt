package kmp.shared.todo.data.source

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.usecase.TaskId
import kotlinx.coroutines.flow.Flow

interface TasksLocalSource {
    suspend fun getAllTasks(): Result<List<Task>>
    fun observerAllTasks(): Flow<List<Task>>
    suspend fun saveAllTasks(value: List<Task>): Result<Boolean>
    suspend fun deleteAllTasks()
    suspend fun getTask(request: TaskId): Result<Task>
    suspend fun saveTask(detail: Task): Result<Boolean>
}