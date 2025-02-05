package kmp.shared.samplecomposemultiplatform.infrastructure.remote

import io.ktor.client.HttpClient
import kmp.shared.base.Result
import kmp.shared.samplecomposemultiplatform.domain.model.Task
import kmp.shared.samplecomposemultiplatform.infrastructure.model.TaskDto
import kmp.shared.samplecomposemultiplatform.infrastructure.model.toDomain

internal class TaskService(private val client: HttpClient) {

    suspend fun getAllTasks(): Result.Success<List<Task>> {
        // TODO: Use real implementation below
        val data = listOf(
            TaskDto(
                userId = 1,
                id = 1,
                title = "Task 1",
                completed = false,
            )
        )
        return Result.Success(data.map(TaskDto::toDomain))
    }
    // TODO: Use real implementation below
//        runCatchingCommonNetworkExceptions {
//            client.post(SAMPLE_PATH) {
//                setBody(body)
//            }.body()
//        }

    private companion object {
        const val ROOT_PATH = "/api"
        const val SAMPLE_PATH = "$ROOT_PATH/sample"
    }
}