package kmp.shared.taskList.data.repository

import kmp.shared.base.Result
import kmp.shared.taskList.data.source.TaskSource
import kmp.shared.taskList.domain.model.Task
import kmp.shared.taskList.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

internal class TasksRepositoryImpl(
    private val source: TaskSource,
) : TasksRepository {

    override fun getTasks(): Flow<Result<List<Task>>> = source.observeTasks()

}