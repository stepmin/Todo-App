package kmp.shared.todo.domain.repository

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.usecase.TaskId
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun observeTasks(): Flow<Result<List<Task>>>
    suspend fun getTaskDetail(taskDetailRequest: TaskDetailRequest): Result<TaskDetail>
    suspend fun changeTaskState(taskId: TaskId): Result<Boolean>
}