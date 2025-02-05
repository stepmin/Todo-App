package kmp.shared.samplecomposemultiplatform.infrastructure.source

import kmp.shared.base.Result
import kmp.shared.samplecomposemultiplatform.data.source.TaskSource
import kmp.shared.samplecomposemultiplatform.domain.model.Task
import kmp.shared.samplecomposemultiplatform.infrastructure.remote.TaskService

internal class TasksSourceImpl(
    private val service: TaskService,
) : TaskSource {

    override suspend fun getTasks(): Result<List<Task>> = service.getAllTasks()
}