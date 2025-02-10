package kmp.shared.todo.domain.usecase

import kmp.shared.base.Result
import kmp.shared.base.usecase.UseCaseResult
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.repository.TasksRepository

interface UpdateTasksTextUseCase : UseCaseResult<Task, Boolean>

class UpdateTasksTextUseCaseImpl(
    val repository: TasksRepository
) : UpdateTasksTextUseCase {

    override suspend fun invoke(taskid: Task): Result<Boolean> = repository.updateTasksText(taskid)
}