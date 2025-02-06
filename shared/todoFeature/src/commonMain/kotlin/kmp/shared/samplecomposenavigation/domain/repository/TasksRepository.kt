package kmp.shared.samplecomposenavigation.domain.repository

import kmp.shared.base.Result
import kmp.shared.samplecomposenavigation.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(): Flow<Result<List<Task>>>
}