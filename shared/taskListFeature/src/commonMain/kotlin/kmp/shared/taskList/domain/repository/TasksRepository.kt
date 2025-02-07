package kmp.shared.taskList.domain.repository

import kmp.shared.base.Result
import kmp.shared.taskList.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(): Flow<Result<List<Task>>>
}