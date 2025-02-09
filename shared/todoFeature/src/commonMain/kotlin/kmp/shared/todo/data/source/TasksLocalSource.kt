package kmp.shared.todo.data.source

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.model.TaskDetailRequest
import kotlinx.coroutines.flow.Flow

interface TasksLocalSource {
    suspend fun getAllTasks(): Result<List<Task>>
    fun observerAllTasks(): Flow<List<Task>>
    suspend fun saveAllTasks(value: List<Task>): Result<Boolean>
    suspend fun deleteAllTasks()
    suspend fun getTaskDetail(request: TaskDetailRequest): Result<TaskDetail>
    suspend fun saveTaskDetail(detail: TaskDetail): Result<Boolean>
}