package kmp.shared.samplecomposenavigation.infrastructure.source

import kmp.shared.base.Result
import kmp.shared.samplecomposenavigation.data.source.TaskSource
import kmp.shared.samplecomposenavigation.domain.model.Task
import kmp.shared.samplecomposenavigation.infrastructure.remote.TaskService

internal class TasksSourceImpl(
    private val service: TaskService,
) : TaskSource {

    override suspend fun getTasks(): Result<List<Task>> = service.getAllTasks()
}