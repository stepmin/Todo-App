package kmp.shared.taskList.presentation.vm

import androidx.lifecycle.viewModelScope
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.taskList.base.vm.BaseViewModel
import kmp.shared.taskList.base.vm.VmEvent
import kmp.shared.taskList.base.vm.VmIntent
import kmp.shared.taskList.base.vm.VmState
import kmp.shared.taskList.domain.model.Task
import kmp.shared.taskList.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TaskListViewModel(
    private val getTasksData: GetTasksUseCase,
) : BaseViewModel<TaskListState, TaskListIntent, TaskListEvent>(TaskListState()) {

    override suspend fun applyIntent(intent: TaskListIntent) {
        when (intent) {
            is TaskListIntent.OnAppeared -> {
                loadData()
            }
            is TaskListIntent.OnButtonTapped -> {
                _events.emit(TaskListEvent.NavigateToTaskDetail(intent.id, intent.userId))
            }
        }
    }

    private suspend fun loadData() {
        update { copy(loading = true) }
        getTasksData.invoke().onEach { result ->
            when (result) {
                is Result.Success -> update { copy(tasks = result.data, loading = false) }
                is Result.Error -> update { copy(error = result.error, loading = false) }
            }
        }.launchIn(viewModelScope)
    }

}

data class TaskListState(
    val loading: Boolean = false,
    val tasks: List<Task>? = null,
    val error: ErrorResult? = null,
) : VmState {
    constructor() : this(true, null, null)
}

sealed interface TaskListIntent : VmIntent {
    data object OnAppeared : TaskListIntent
    data class OnButtonTapped(val id: Int, val userId: Int) : TaskListIntent
}

sealed interface TaskListEvent : VmEvent {
    data class ShowMessage(val message: String) : TaskListEvent
    data class NavigateToTaskDetail(val id: Int, val userId: Int) : TaskListEvent
}