package kmp.shared.todo.infrastructure.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kmp.shared.base.Result
import kmp.shared.base.error.util.runCatchingCommonNetworkExceptions
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.infrastructure.model.TaskDetailDto
import kmp.shared.todo.infrastructure.model.TaskDto
import kmp.shared.todo.domain.model.TaskPatch
import kmp.shared.todo.infrastructure.model.UserDetailDto
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

interface TasksService {
    suspend fun getAllTasks(): Result<List<Task>>
    suspend fun getTaskDetail(taskDetailRequest: TaskDetailRequest): Result<TaskDetail>
    suspend fun changeTaskState(taskId: TaskPatch): Result<Boolean>
}

internal class TasksServiceImpl(private val client: HttpClient) : TasksService {

    override suspend fun getAllTasks(): Result<List<Task>> = runCatchingCommonNetworkExceptions {
        client.get(TODOS_LIST_PATH) {}.body<List<TaskDto>>().map {
            Task(it.id, it.userId, it.title, it.completed)
        }
    }

    override suspend fun getTaskDetail(taskDetailRequest: TaskDetailRequest): Result<TaskDetail> = runCatchingCommonNetworkExceptions {
        coroutineScope {
            val taskDetailResponse = async {
                val taskId = taskDetailRequest.id
                client.get("$ROOT_PATH_TODOS/$taskId") {}.body<TaskDetailDto>()
            }
            val userDetailResponse = async {
                val userId = taskDetailRequest.userId
                client.get("$ROOT_PATH_USERS/$userId").body<UserDetailDto>()
            }

            val taskDetailDto = taskDetailResponse.await()
            val userDetailDto = userDetailResponse.await()

            TaskDetail(
                id = taskDetailDto.id,
                userId = taskDetailDto.userId,
                title = taskDetailDto.title,
                completed = taskDetailDto.completed,
                name = userDetailDto.name,
                username = userDetailDto.username,
                email = userDetailDto.email,
            )
        }

    }

    override suspend fun changeTaskState(task: TaskPatch): Result<Boolean> = runCatchingCommonNetworkExceptions {
        client.patch("$TODOS_LIST_PATH${task.id}") {
            setBody(
                TaskPatch(
                    id = task.id,
                    completed = task.completed
                )
            )
        }.status == HttpStatusCode.OK
    }

    private companion object {
        const val ROOT_PATH_TODOS = "/todos"
        const val TODOS_LIST_PATH = "$ROOT_PATH_TODOS/"
        const val ROOT_PATH_USERS = "/users"
    }
}