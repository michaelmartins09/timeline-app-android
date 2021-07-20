package br.com.timeline.app.android.viewmodel

import androidx.lifecycle.*
import br.com.timeline.app.android.entities.Task
import br.com.timeline.app.android.services.repository.TaskRepository
import br.com.timeline.app.android.utils.enums.TaskType
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val lastTaskTypeSelected: MutableLiveData<TaskType> by lazy { MutableLiveData<TaskType>() }

    val allTasks: LiveData<List<Task>> = repository.allTasks.asLiveData()

    val countVisits: LiveData<Int> = repository.getSizeByTaskType(TaskType.VISIT).asLiveData()

    val countCalls: LiveData<Int> = repository.getSizeByTaskType(TaskType.CALL).asLiveData()

    val countMails: LiveData<Int> = repository.getSizeByTaskType(TaskType.MAIL).asLiveData()

    val countProposals: LiveData<Int> = repository.getSizeByTaskType(TaskType.PROPOSAL).asLiveData()

    val countReunion: LiveData<Int> = repository.getSizeByTaskType(TaskType.REUNION).asLiveData()

    val countMore: LiveData<Int> = repository.getSizeByTaskType(TaskType.MORE).asLiveData()

    fun insert(task: Task) = viewModelScope.launch { repository.insert(task) }

    fun deleteAll() = viewModelScope.launch { repository.deleteAll() }

}