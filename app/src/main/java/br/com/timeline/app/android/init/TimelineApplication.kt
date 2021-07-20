package br.com.timeline.app.android.init

import android.app.Application
import br.com.timeline.app.android.services.database.TaskRoomDatabase
import br.com.timeline.app.android.services.repository.TaskRepository
import kotlinx.coroutines.*

class TimelineApplication : Application() {
    val database by lazy { TaskRoomDatabase.getDatabase(this)}
    val repository by lazy { TaskRepository(database.taskDAO()) }
}