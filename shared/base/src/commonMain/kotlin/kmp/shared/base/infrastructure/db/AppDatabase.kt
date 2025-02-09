package kmp.shared.base.infrastructure.db

import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import kmp.shared.base.infrastructure.db.entities.TaskEntity
import kotlinx.coroutines.flow.Flow

@Database(entities = [TaskEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTodoDao(): TodoDao
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

    @Query("SELECT count(*) FROM tasks_table")
    suspend fun count(): Int

    @Query("SELECT * FROM tasks_table")
    fun observerTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks_table")
    suspend fun getAll(): List<TaskEntity>

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAll()

}