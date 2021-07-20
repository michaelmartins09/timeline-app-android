package br.com.timeline.app.android.services.repository

import androidx.annotation.WorkerThread
import br.com.timeline.app.android.services.dao.ITaskDAO
import br.com.timeline.app.android.entities.Task
import br.com.timeline.app.android.utils.enums.TaskType
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val iTaskDAO: ITaskDAO) {
    val allTasks: Flow<List<Task>> = iTaskDAO.getTasks()

    @WorkerThread
    suspend fun insert(task: Task) = iTaskDAO.insert(task)

    @WorkerThread
    suspend fun deleteAll() = iTaskDAO.deleteAll()

    fun getSizeByTaskType(type: TaskType): Flow<Int> = iTaskDAO.getSizeByType(type)
}