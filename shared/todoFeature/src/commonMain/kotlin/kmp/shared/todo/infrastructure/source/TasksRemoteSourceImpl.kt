package kmp.shared.todo.infrastructure.source

import kmp.shared.base.Result
import kmp.shared.todo.data.source.TasksRemoteSource
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.usecase.TaskId
import kmp.shared.todo.domain.model.TaskPatch
import kmp.shared.todo.infrastructure.remote.TasksService

internal class TasksRemoteSourceImpl(
    private val tasksService: TasksService,
) : TasksRemoteSource {

    override suspend fun getAllTasks(): Result<List<Task>> = tasksService.getAllTasks()

    override suspend fun getTask(taskDetailRequest: TaskId): Result<Task> {
        return tasksService.getTask(taskDetailRequest)
    }

    override suspend fun changeTaskState(task: TaskPatch): Result<Boolean> {
        return tasksService.changeTaskState(task)
    }
}