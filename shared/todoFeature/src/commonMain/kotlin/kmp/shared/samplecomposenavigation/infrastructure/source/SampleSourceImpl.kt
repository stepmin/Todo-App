package kmp.shared.samplecomposenavigation.infrastructure.source

import kmp.shared.base.Result
import kmp.shared.base.error.domain.CommonError
import kmp.shared.samplecomposenavigation.data.source.TaskSource
import kmp.shared.samplecomposenavigation.domain.model.Task
import kmp.shared.samplecomposenavigation.infrastructure.db.TaskLocalSource
import kmp.shared.samplecomposenavigation.infrastructure.remote.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class TasksSourceImpl(
    private val taskLocalSource: TaskLocalSource,
    private val taskService: TaskService,
) : TaskSource {

    override fun observeTasks(): Flow<Result<List<Task>>> =
        flow {
            //TODO-data from DB
//            emit(taskLocalSource.getAllTasks())
            try {
                val remoteTasks = taskService.getAllTasks()
                if (remoteTasks is Result.Success) {
                    taskLocalSource.saveAllTasks(remoteTasks)
                }
                emit(remoteTasks)
            } catch (e: Exception) {
                // Handle remote exception
                emit(Result.Error(CommonError.NetworkConnectionError(e)))
            }
        }
//            .onStart { emit(Result.loading()) }.catch { emit(Result.failure(it)) }
}