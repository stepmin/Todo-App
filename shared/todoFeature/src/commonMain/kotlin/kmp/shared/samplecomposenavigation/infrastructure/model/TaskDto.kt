package kmp.shared.samplecomposenavigation.infrastructure.model

import kmp.shared.samplecomposenavigation.domain.model.Task
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDto(
    @SerialName("userId")
    val userId: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("completed")
    val completed: Boolean = false,
)

internal fun TaskDto.toDomain(): Task = Task(userId, id, title, completed)
