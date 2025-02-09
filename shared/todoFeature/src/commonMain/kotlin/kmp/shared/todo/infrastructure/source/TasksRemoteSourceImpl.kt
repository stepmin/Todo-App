package kmp.shared.todo.infrastructure.source

import kmp.shared.base.Result
import kmp.shared.todo.data.source.TasksRemoteSource
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.infrastructure.remote.TasksService

internal class TasksRemoteSourceImpl(
    private val tasksService: TasksService,
) : TasksRemoteSource {

    override suspend fun getAllTasks(): Result<List<Task>> = tasksService.getAllTasks()

    override suspend fun getTaskDetail(taskDetailRequest: TaskDetailRequest): Result<TaskDetail> {
        return tasksService.getTaskDetail(taskDetailRequest)
    }
}