package kmp.shared.todo.data.repository

import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.todo.data.source.TasksLocalSource
import kmp.shared.todo.data.source.TasksRemoteSource
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.repository.TasksRepository
import kmp.shared.todo.domain.usecase.TaskId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class TasksRepositoryImpl(
    private val remoteSource: TasksRemoteSource,
    private val localSource: TasksLocalSource,
) : TasksRepository {

    override fun observeTasks(): Flow<Result<List<Task>>> = flow {
        try {
            val localData = localSource.getAllTasks()
            if (localData is Result.Success && localData.data.isNotEmpty()) {
                emit(Result.Success(localData.data))
            }
            val remoteTasks = remoteSource.getAllTasks()
            if (remoteTasks is Result.Success) {
                localSource.deleteAllTasks()
                localSource.saveAllTasks(remoteTasks.data)
            }
            emit(remoteTasks)
        } catch (e: Exception) {
            println("Exception: $e")
            emit(Result.Error(CommonError.NetworkConnectionError(e)))
        }
    }

    override suspend fun getTaskDetail(taskDetailRequest: TaskDetailRequest): Result<TaskDetail> = remoteSource.getTaskDetail(taskDetailRequest)

    override suspend fun changeTaskState(taskId: TaskId): Result<Boolean> {
        return remoteSource.changeTaskState(taskId)
    }

}