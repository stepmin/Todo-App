package kmp.shared.todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskPatch(
    val id: Int,
    val completed: Boolean? = null,
    val text: String? = null
)