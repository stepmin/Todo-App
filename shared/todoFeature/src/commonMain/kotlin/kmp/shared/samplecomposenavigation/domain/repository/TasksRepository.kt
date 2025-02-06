package kmp.shared.samplecomposenavigation.domain.repository

import kmp.shared.base.Result
import kmp.shared.samplecomposenavigation.domain.model.Task

interface TasksRepository {
    suspend fun getTasks(): Result<List<Task>>
}