package kmp.shared.todo.data.source

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.domain.model.TaskPatch

internal interface TasksRemoteSource {
    suspend fun getAllTasks(): Result<List<Task>>
    suspend fun getTaskDetail(taskDetailRequest: TaskDetailRequest): Result<TaskDetail>
    suspend fun changeTaskState(taskId: TaskPatch): Result<Boolean>
}