package kmp.shared.samplecomposemultiplatform.data.source

import kmp.shared.base.Result
import kmp.shared.samplecomposemultiplatform.domain.model.Task

internal interface TaskSource {
    suspend fun getTasks(): Result<List<Task>>
}