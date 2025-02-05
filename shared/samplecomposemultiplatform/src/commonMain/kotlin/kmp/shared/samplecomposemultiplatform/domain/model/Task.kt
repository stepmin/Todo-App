package kmp.shared.samplecomposemultiplatform.domain.model

data class Task(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean = false,
)
