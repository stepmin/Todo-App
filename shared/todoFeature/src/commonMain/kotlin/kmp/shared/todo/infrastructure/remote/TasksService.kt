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
import kmp.shared.todo.domain.usecase.TaskId
import kmp.shared.todo.infrastructure.model.TaskDto
import kmp.shared.todo.domain.model.TaskPatch
import kotlinx.coroutines.coroutineScope

interface TasksService {
    suspend fun getAllTasks(): Result<List<Task>>
    suspend fun getTask(taskDetailRequest: TaskId): Result<Task>
    suspend fun changeTaskState(taskId: TaskPatch): Result<Boolean>
}

internal class TasksServiceImpl(private val client: HttpClient) : TasksService {

    override suspend fun getAllTasks(): Result<List<Task>> = runCatchingCommonNetworkExceptions {
        client.get(TODOS_LIST_PATH) {}.body<List<TaskDto>>().map {
            Task(it.id, it.userId, it.title, it.completed)
        }
    }

    override suspend fun getTask(taskId: TaskId): Result<Task> = runCatchingCommonNetworkExceptions {
        coroutineScope {
            val task = client.get("$ROOT_PATH_TODOS/$taskId") {}.body<TaskDto>()

            Task(
                id = task.id,
                userId = task.userId,
                title = task.title,
                completed = task.completed,
            )
        }

    }

    override suspend fun changeTaskState(task: TaskPatch): Result<Boolean> = runCatchingCommonNetworkExceptions {
        client.patch("$TODOS_PATH${task.id}") {
            setBody(
                TaskPatch(
                    id = task.id,
                    completed = task.completed,
                ),
            )
        }.status == HttpStatusCode.OK
    }

    private companion object {
        const val ROOT_PATH_TODOS = "/todos"
        //user id = 1 for testing reasons
        const val TODOS_LIST_PATH = "$ROOT_PATH_TODOS/?userId=1"
        const val TODOS_PATH = "$ROOT_PATH_TODOS/"
    }
}