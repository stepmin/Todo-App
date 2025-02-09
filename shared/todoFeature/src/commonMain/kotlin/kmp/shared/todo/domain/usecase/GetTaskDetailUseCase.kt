package kmp.shared.todo.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseFlowResult
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

interface GetTaskDetailUseCase : UseCaseFlowResult<TaskDetailRequest, TaskDetail>

class GetTaskDetailUseCaseImpl(
    val repository: TasksRepository
) : GetTaskDetailUseCase {

    override suspend fun invoke(taskDetailRequest: TaskDetailRequest): Flow<Result<TaskDetail>> = repository.observeTaskDetail(taskDetailRequest)
}