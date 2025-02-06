package kmp.shared.samplecomposenavigation.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseResultNoParams
import kmp.shared.samplecomposenavigation.domain.model.Task
import kmp.shared.samplecomposenavigation.domain.repository.TasksRepository

interface GetTasksUseCase : UseCaseResultNoParams<List<Task>>

//TODO-rename
class GetTasksUseCaseImpl(
    val repository: TasksRepository
) : GetTasksUseCase {

    override suspend fun invoke(): Result<List<Task>> = repository.getTasks()
}