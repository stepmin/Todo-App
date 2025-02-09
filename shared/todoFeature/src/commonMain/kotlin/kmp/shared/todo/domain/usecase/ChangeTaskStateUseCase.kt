package kmp.shared.todo.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseResult
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.repository.TasksRepository

interface ChangeTaskStateUseCase : UseCaseResult<Task, Boolean>

class ChangeTaskStateUseCaseImpl(
    val repository: TasksRepository
) : ChangeTaskStateUseCase {
    override suspend fun invoke(task: Task): Result<Boolean> = repository.changeTaskState(task)

}