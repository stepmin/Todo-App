package kmp.shared.samplecomposenavigation.data.source

import kmp.shared.base.Result
import kmp.shared.samplecomposenavigation.domain.model.Task

internal interface TaskSource {
    suspend fun getTasks(): Result<List<Task>>
}