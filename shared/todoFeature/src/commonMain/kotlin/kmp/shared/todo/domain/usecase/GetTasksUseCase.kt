package kmp.shared.todo.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseFlowResultNoParams
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

interface GetTasksUseCase : UseCaseFlowResultNoParams<List<Task>>

class GetTasksUseCaseImpl(
    val repository: TasksRepository
) : GetTasksUseCase {

    override suspend fun invoke(): Flow<Result<List<Task>>> = repository.observeTasks()
}