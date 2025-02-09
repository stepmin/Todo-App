package kmp.shared.todo.data.source

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.usecase.TaskId
import kmp.shared.todo.domain.model.TaskPatch

internal interface TasksRemoteSource {
    suspend fun getAllTasks(): Result<List<Task>>
    suspend fun getTask(taskDetailRequest: TaskId): Result<Task>
    suspend fun changeTaskState(taskId: TaskPatch): Result<Boolean>
}