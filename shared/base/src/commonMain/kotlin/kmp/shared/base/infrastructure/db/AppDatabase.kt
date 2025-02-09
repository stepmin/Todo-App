package kmp.shared.base.infrastructure.db

import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import kmp.shared.base.infrastructure.db.entities.TaskDetailEntity
import kmp.shared.base.infrastructure.db.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Database(entities = [TaskEntity::class, TaskDetailEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskListDao(): TodoDao
    abstract fun getTaskDetailDao(): TaskDetailDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TaskEntity>)

    @Query("SELECT count(*) FROM tasks")
    suspend fun count(): Int

    @Query("SELECT * FROM tasks")
    fun observerTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks")
    suspend fun getAll(): List<TaskEntity>

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}

@Dao
interface TaskDetailDao {
    @Query("SELECT * FROM task_detail WHERE id = :taskId AND userId = :userId")
    fun getTaskDetail(taskId: Int, userId: Int): TaskDetailEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTaskDetail(detail: TaskDetailEntity)

}