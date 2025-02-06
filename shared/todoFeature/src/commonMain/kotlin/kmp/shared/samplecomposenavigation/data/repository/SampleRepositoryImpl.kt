package kmp.shared.samplecomposenavigation.data.repository

import kmp.shared.base.Result
import kmp.shared.samplecomposenavigation.data.source.TaskSource
import kmp.shared.samplecomposenavigation.domain.model.Task
import kmp.shared.samplecomposenavigation.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

internal class TasksRepositoryImpl(
    private val source: TaskSource,
) : TasksRepository {

    override fun getTasks(): Flow<Result<List<Task>>> = source.observeTasks()

}