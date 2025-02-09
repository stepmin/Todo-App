package kmp.shared.todo.domain.repository

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun observeTasks(): Flow<Result<List<Task>>>
    fun observeTaskDetail(taskDetailRequest: TaskDetailRequest): Flow<Result<TaskDetail>>
}