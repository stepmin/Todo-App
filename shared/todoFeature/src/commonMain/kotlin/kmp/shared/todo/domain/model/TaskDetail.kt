package kmp.shared.todo.domain.model

data class TaskDetail(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean = false,
    val name: String,
    val username: String,
    val email: String,
)
