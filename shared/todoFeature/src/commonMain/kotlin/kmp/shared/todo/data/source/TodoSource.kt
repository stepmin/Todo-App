package kmp.shared.todo.data.source

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.DetailRequest
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kotlinx.coroutines.flow.Flow

internal interface TodoSource {
    fun observeTasks(): Flow<Result<List<Task>>>
    suspend fun getTaskDetail(detailRequest: DetailRequest): Result<TaskDetail>
}