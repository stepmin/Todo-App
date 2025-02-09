package kmp.shared.todo.domain.model

data class TaskDetail(
    val id: Int,
    val userId: Int? = null,
    val title: String? = null,
    val completed: Boolean,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null,
)
