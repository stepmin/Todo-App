package kmp.shared.todo.domain.model

data class Task(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean,
) {
    fun toggleCompleted(): Task {
        return copy(completed = !completed)
    }

    fun updateTitle(newTitle: String): Task {
        return copy(title = newTitle)
    }
}