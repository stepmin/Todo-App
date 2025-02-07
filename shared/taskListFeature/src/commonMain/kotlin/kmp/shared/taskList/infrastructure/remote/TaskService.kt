package kmp.shared.taskList.infrastructure.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kmp.shared.base.Result
import kmp.shared.base.error.util.runCatchingCommonNetworkExceptions
import kmp.shared.taskList.domain.model.Task
import kmp.shared.taskList.infrastructure.model.TaskDto

internal class TaskService(private val client: HttpClient) {

    suspend fun getAllTasks(): Result<List<Task>> = runCatchingCommonNetworkExceptions {
        client.get(SAMPLE_PATH) {}.body<List<TaskDto>>().map {
            Task(it.userId, it.id, it.title, it.completed)
        }
    }

    private companion object {
        const val ROOT_PATH = "/todos"
        const val SAMPLE_PATH = "$ROOT_PATH/"
    }
}