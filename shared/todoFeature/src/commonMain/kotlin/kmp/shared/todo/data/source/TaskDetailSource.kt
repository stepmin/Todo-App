package kmp.shared.todo.data.source

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.TaskDetail

internal interface TaskDetailSource {
    fun getTaskDetail(): Result<TaskDetail>
}