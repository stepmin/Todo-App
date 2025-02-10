package kmp.shared.todo.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseFlowResult
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

typealias TaskId = Int

interface GetTaskDetailUseCase : UseCaseFlowResult<TaskId, Task>

class GetTaskUseCaseImpl(
    val repository: TasksRepository
) : GetTaskDetailUseCase {

    override suspend fun invoke(taskId: TaskId): Flow<Result<Task>> = repository.observeTaskDetail(taskId)
}