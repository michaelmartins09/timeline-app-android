package br.com.timeline.app.android.services.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.timeline.app.android.entities.Task
import br.com.timeline.app.android.utils.TaskType
import kotlinx.coroutines.flow.Flow

@Dao
interface ITaskDAO {
    @Query("SELECT * FROM tasks ORDER BY date ASC")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT COUNT(*) FROM tasks WHERE tasks.type = :type")
    fun getSizeByType(type: TaskType): Flow<Int>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}