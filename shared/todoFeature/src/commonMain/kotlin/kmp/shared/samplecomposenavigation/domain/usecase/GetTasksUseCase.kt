package kmp.shared.samplecomposenavigation.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseFlowResultNoParams
import kmp.shared.samplecomposenavigation.domain.model.Task
import kmp.shared.samplecomposenavigation.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

interface GetTasksUseCase : UseCaseFlowResultNoParams<List<Task>>

class GetTasksUseCaseImpl(
    val repository: TasksRepository
) : GetTasksUseCase {

    override suspend fun invoke(): Flow<Result<List<Task>>> = repository.getTasks()
}