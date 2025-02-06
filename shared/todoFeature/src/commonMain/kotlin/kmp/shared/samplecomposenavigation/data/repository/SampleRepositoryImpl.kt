package kmp.shared.samplecomposenavigation.data.repository

import kmp.shared.base.Result
import kmp.shared.samplecomposenavigation.data.source.TaskSource
import kmp.shared.samplecomposenavigation.domain.model.Task
import kmp.shared.samplecomposenavigation.domain.repository.TasksRepository

internal class TasksRepositoryImpl(
    private val source: TaskSource,
) : TasksRepository {

    override suspend fun getTasks(): Result<List<Task>> = source.getTasks()
}