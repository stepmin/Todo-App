package kmp.shared.todo.data.repository

import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.todo.data.source.TasksLocalSource
import kmp.shared.todo.data.source.TasksRemoteSource
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.usecase.TaskId
import kmp.shared.todo.domain.model.TaskPatch
import kmp.shared.todo.domain.repository.TasksRepository
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
            println("exception when observing tasks: $e")
            emit(Result.Error(CommonError.NetworkConnectionError(e)))
        }
    }

    override fun observeTaskDetail(taskDetailRequest: TaskId): Flow<Result<Task>> = flow {
        try {
            val localData = localSource.getTask(taskDetailRequest)
            if (localData is Result.Success) {
                emit(localData)
            }
            val remoteTaskDetail = remoteSource.getTask(taskDetailRequest)
            if (remoteTaskDetail is Result.Success) {
                localSource.saveTask(remoteTaskDetail.data)
            }
            emit(remoteTaskDetail)
        } catch (e: Exception) {
            println("exception when observing task detail: $e")
            emit(Result.Error(CommonError.NetworkConnectionError(e)))
        }
    }

    override suspend fun changeTaskState(task: Task): Result<Boolean> {
        val changeTaskState = remoteSource.changeTaskState(TaskPatch(id = task.id, completed = task.completed))
        if (changeTaskState is Result.Success) {
            localSource.saveTask(task)
        }
        return changeTaskState
    }

    override suspend fun updateTasksText(task: Task): Result<Boolean> {
        val updateTasksText = remoteSource.changeTaskState(TaskPatch(id = task.id, text = task.title))
        if (updateTasksText is Result.Success) {
            localSource.saveTask(task)
        }
        return updateTasksText
    }
}