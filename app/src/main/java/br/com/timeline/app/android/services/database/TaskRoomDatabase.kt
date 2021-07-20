package br.com.timeline.app.android.services.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.timeline.app.android.services.dao.ITaskDAO
import br.com.timeline.app.android.entities.Task
import br.com.timeline.app.android.utils.TaskType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskRoomDatabase: RoomDatabase() {
    abstract fun taskDAO(): ITaskDAO

    companion object {
        @Volatile
        private var INSTANCE: TaskRoomDatabase? = null

        fun getDatabase(context: Context): TaskRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskRoomDatabase::class.java,
                    "tasks_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}