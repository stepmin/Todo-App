package kmp.shared.taskList.data.source

import kmp.shared.base.Result
import kmp.shared.taskList.domain.model.Task
import kotlinx.coroutines.flow.Flow

internal interface TaskSource {
    fun observeTasks(): Flow<Result<List<Task>>>
}