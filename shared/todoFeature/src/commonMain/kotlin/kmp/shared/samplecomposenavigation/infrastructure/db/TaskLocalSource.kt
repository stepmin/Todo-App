package kmp.shared.samplecomposenavigation.infrastructure.db

import kmp.shared.base.Result
import kmp.shared.base.error.util.runCatchingCommonNetworkExceptions
import kmp.shared.samplecomposenavigation.domain.model.Task

class TaskLocalSource {
    suspend fun getAllTasks(): Result<List<Task>> {
        TODO("Not yet implemented")
//        return runCatchingCommonNetworkExceptions {
//            listOf(Task(1, 1, "title", false))
//        }
    }

    fun saveAllTasks(value: Any) {
//        TODO("Not yet implemented")
    }
}