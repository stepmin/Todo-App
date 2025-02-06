package kmp.shared.samplecomposenavigation.data.source

import kmp.shared.base.Result
import kmp.shared.samplecomposenavigation.domain.model.Task
import kotlinx.coroutines.flow.Flow

internal interface TaskSource {
    fun observeTasks(): Flow<Result<List<Task>>>
}