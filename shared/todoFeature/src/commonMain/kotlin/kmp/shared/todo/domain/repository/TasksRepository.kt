package kmp.shared.todo.domain.repository

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.usecase.TaskId
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun observeTasks(): Flow<Result<List<Task>>>
    fun observeTaskDetail(taskDetailRequest: TaskId): Flow<Result<Task>>
    suspend fun changeTaskState(taskId: Task): Result<Boolean>
    suspend fun updateTasksText(taskId: Task): Result<Boolean>
}