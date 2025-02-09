package kmp.shared.base.infrastructure.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_detail")
data class TaskDetailEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean = false,
    val name: String,
    val userName: String,
    val email: String,
)