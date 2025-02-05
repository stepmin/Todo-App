package kmp.shared.samplecomposemultiplatform.domain.repository

import kmp.shared.base.Result
import kmp.shared.samplecomposemultiplatform.domain.model.Task

interface TasksRepository {
    suspend fun getTasks(): Result<List<Task>>
}