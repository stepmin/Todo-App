package kmp.shared.todo.domain.repository

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.DetailRequest
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTaskList(): Flow<Result<List<Task>>>
    suspend fun getTaskDetail(detailRequest: DetailRequest): Result<TaskDetail>
}