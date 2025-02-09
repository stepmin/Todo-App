package kmp.shared.todo.infrastructure.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kmp.shared.base.Result
import kmp.shared.base.error.util.runCatchingCommonNetworkExceptions
import kmp.shared.todo.domain.model.DetailRequest
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.infrastructure.model.TaskDetailDto
import kmp.shared.todo.infrastructure.model.TaskDto
import kmp.shared.todo.infrastructure.model.UserDetailDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

internal class TaskService(private val client: HttpClient) {

    suspend fun getAllTasks(): Result<List<Task>> = runCatchingCommonNetworkExceptions {
        client.get(TASK_LIST_PATH) {}.body<List<TaskDto>>().map {
            Task(it.id, it.userId, it.title, it.completed)
        }
    }

    suspend fun getTaskDetail(detailRequest: DetailRequest): Result<TaskDetail> = runCatchingCommonNetworkExceptions {
        coroutineScope {
            val taskDetailResponse = async {
                val taskId = detailRequest.id
                client.get("$ROOT_PATH_TODOS/$taskId") {}.body<TaskDetailDto>()
            }
            val userDetailResponse = async {
                val userId = detailRequest.userId
                client.get("$ROOT_PATH_USERS/$userId").body<UserDetailDto>()
            }

            val taskDetailDto = taskDetailResponse.await()
            val userDetailDto = userDetailResponse.await()

            TaskDetail(
                id = taskDetailDto.id,
                title = taskDetailDto.title,
                completed = taskDetailDto.completed,
                name = userDetailDto.name,
                username = userDetailDto.username,
                email = userDetailDto.email
            )
        }

    }

    private companion object {
        const val ROOT_PATH_TODOS = "/todos"
        const val TASK_LIST_PATH = "$ROOT_PATH_TODOS/"
        const val ROOT_PATH_USERS = "/users"
    }
}