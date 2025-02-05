package kmp.shared.samplecomposemultiplatform.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseResultNoParams
import kmp.shared.samplecomposemultiplatform.domain.model.Task
import kmp.shared.samplecomposemultiplatform.domain.repository.TasksRepository

interface GetTasksUseCase : UseCaseResultNoParams<List<Task>>

//TODO-rename
class GetTasksUseCaseImpl(
    val repository: TasksRepository
) : GetTasksUseCase {

    override suspend fun invoke(): Result<List<Task>> = repository.getTasks()
}