package kmp.shared.todo.presentation.vm

import androidx.lifecycle.viewModelScope
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.todo.base.vm.BaseViewModel
import kmp.shared.todo.base.vm.VmEvent
import kmp.shared.todo.base.vm.VmIntent
import kmp.shared.todo.base.vm.VmState
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.usecase.ChangeTaskStateUseCase
import kmp.shared.todo.domain.usecase.GetTasksUseCase
import kmp.shared.todo.presentation.vm.TaskListEvent.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TaskListViewModel(
    private val getTasksData: GetTasksUseCase,
    private val changeTaskState: ChangeTaskStateUseCase,
) : BaseViewModel<TaskListState, TaskListIntent, TaskListEvent>(TaskListState()) {

    init {
        onIntent(TaskListIntent.OnInit)
    }

    override suspend fun applyIntent(intent: TaskListIntent) {
        when (intent) {
            is TaskListIntent.OnInit -> {
                loadData()
            }
            is TaskListIntent.OnAppeared -> {
                //TODO-remove - not used
            }
            is TaskListIntent.OnRowTapped -> {
                _events.emit(NavigateToTaskDetail(intent.id, intent.userId))
            }
            is TaskListIntent.OnTaskCheckTapped -> {
                changeTaskState(intent.task)
            }
        }
    }

    private suspend fun loadData() {
        update { copy(loading = true) }
        getTasksData().onEach { result ->
            when (result) {
                is Result.Success -> update { copy(tasks = result.data, loading = false) }
                is Result.Error -> update { copy(error = result.error, loading = false) }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun changeTaskState(task: Task) {
        update { copy(tasks = tasks?.map { if (it.id == task.id) task.copy(completed = !it.completed) else it }) }
        val invoke = changeTaskState.invoke(task)
        //TODO-error handling in case api start working
        when (invoke) {
            is Result.Success -> {
                println("state changed successfully")
            }
            is Result.Error-> {
                println("state changed was not successful")
            }
        }
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
    data object OnInit : TaskListIntent
    data object OnAppeared : TaskListIntent
    data class OnRowTapped(val id: Int, val userId: Int) : TaskListIntent
    data class OnTaskCheckTapped(val task: Task) : TaskListIntent
}

sealed interface TaskListEvent : VmEvent {
    data class ShowMessage(val message: String) : TaskListEvent
    data class NavigateToTaskDetail(val taskId: Int, val userId: Int) : TaskListEvent
}