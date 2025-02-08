package kmp.shared.todo.infrastructure.model

import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.model.TaskDetail
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDetailDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("completed")
    val completed: Boolean = false,
)
