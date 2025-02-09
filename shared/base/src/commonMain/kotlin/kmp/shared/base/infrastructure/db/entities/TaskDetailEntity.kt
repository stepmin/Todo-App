package kmp.shared.base.infrastructure.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_detail")
data class TaskDetailEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int? = null,
    val title: String? = null,
    val completed: Boolean = false,
    val name: String? = null,
    val userName: String? = null,
    val email: String? = null,
)