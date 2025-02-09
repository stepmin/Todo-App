package kmp.shared.todo.infrastructure.source

import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.todo.data.source.TodoSource
import kmp.shared.todo.domain.model.DetailRequest
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.infrastructure.db.TaskLocalSource
import kmp.shared.todo.infrastructure.remote.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class TodoSourceImpl(
    private val taskLocalSource: TaskLocalSource,
    private val taskService: TaskService,
) : TodoSource {

    override fun observeTasks(): Flow<Result<List<Task>>> =
        flow {
            try {
                val localData = taskLocalSource.getAllTasks()
                if (localData.isNotEmpty()) {
                    emit(Result.Success(localData))
                }
                val remoteTasks = taskService.getAllTasks()
                if (remoteTasks is Result.Success) {
                    taskLocalSource.deleteAllTasks()
                    taskLocalSource.saveAllTasks(remoteTasks.data)
                }
                emit(remoteTasks)
            } catch (e: Exception) {
                // Handle remote exception
                println("Exception: $e")
                emit(Result.Error(CommonError.NetworkConnectionError(e)))
            }
        }

    override suspend fun getTaskDetail(detailRequest: DetailRequest): Result<TaskDetail> {
        return taskService.getTaskDetail(detailRequest)
    }
}