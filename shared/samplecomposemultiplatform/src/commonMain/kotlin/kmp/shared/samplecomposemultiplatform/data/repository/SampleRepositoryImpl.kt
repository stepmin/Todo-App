package kmp.shared.samplecomposemultiplatform.data.repository

import kmp.shared.base.Result
import kmp.shared.samplecomposemultiplatform.data.source.TaskSource
import kmp.shared.samplecomposemultiplatform.domain.model.Task
import kmp.shared.samplecomposemultiplatform.domain.repository.TasksRepository

internal class TasksRepositoryImpl(
    private val source: TaskSource,
) : TasksRepository {

    override suspend fun getTasks(): Result<List<Task>> = source.getTasks()
}