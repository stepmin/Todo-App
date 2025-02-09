package kmp.shared.todo.infrastructure.db

import kmp.shared.base.Result
import kmp.shared.todo.domain.model.Task

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