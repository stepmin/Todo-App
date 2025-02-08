package kmp.shared.todo.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseResult
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.repository.TasksRepository

interface GetTaskDetailUseCase : UseCaseResult<Pair<Int, Int>, TaskDetail>

class GetTaskDetailUseCaseImpl(
    val repository: TasksRepository
) : GetTaskDetailUseCase {

    override suspend fun invoke(params: Pair<Int, Int>): Result<TaskDetail> = repository.getTaskDetail()
}