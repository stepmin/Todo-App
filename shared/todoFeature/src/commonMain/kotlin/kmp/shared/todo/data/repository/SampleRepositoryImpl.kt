package kmp.shared.todo.data.repository

import kmp.shared.base.Result
import kmp.shared.todo.data.source.TodoSource
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

internal class TasksRepositoryImpl(
    private val source: TodoSource,
) : TasksRepository {

    override fun getTaskList(): Flow<Result<List<Task>>> = source.observeTasks()
    override suspend fun getTaskDetail(): Result<TaskDetail> = source.getTaskDetail()

}