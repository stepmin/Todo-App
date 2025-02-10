package kmp.shared.todo.presentation.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.todo.base.vm.BaseViewModel
import kmp.shared.todo.base.vm.VmEvent
import kmp.shared.todo.base.vm.VmIntent
import kmp.shared.todo.base.vm.VmState
import kmp.shared.todo.domain.model.Task
import kmp.shared.todo.domain.usecase.ChangeTaskStateUseCase
import kmp.shared.todo.domain.usecase.GetTaskDetailUseCase
import kmp.shared.todo.domain.usecase.TaskId
import kmp.shared.todo.domain.usecase.UpdateTasksTextUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val getTaskDetailUseCase: GetTaskDetailUseCase,
    private val changeTaskStateUseCase: ChangeTaskStateUseCase,
    private val updateTasksTextUseCase: UpdateTasksTextUseCase,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskDetailState, TaskDetailIntent, TaskDetailEvent>(TaskDetailState()) {

    override suspend fun applyIntent(intent: TaskDetailIntent) {
        when (intent) {
            TaskDetailIntent.OnInit -> {
                val taskId = savedStateHandle.get<TaskId>("taskId")
                taskId?.let { loadData(it) }
            }

            is TaskDetailIntent.OnAppeared -> {
                //not used
            }

            is TaskDetailIntent.OnTaskButtonTapped -> {
                val updatedTask = intent.task.toggleCompleted()
                update { copy(task = updatedTask) }
                viewModelScope.launch {
                    changeTaskStateUseCase(updatedTask)
                    if (updatedTask.completed) {
                        _events.emit(TaskDetailEvent.NavigateBackAfterChange)
                    }
                }
            }

            is TaskDetailIntent.OnNoteChange -> {
                val updatedTask = state.value.task?.updateTitle(intent.text)
                state.value = state.value.copy(task = updatedTask)
            }

            TaskDetailIntent.OnCheckButtonClicked -> {
                state.value.task?.let {
                    updateTasksTextUseCase(it)
                }
            }
        }
    }

    private suspend fun loadData(input: TaskId) {
        update { copy(loading = true) }
        getTaskDetailUseCase(input).onEach { result ->
            when (result) {
                is Result.Success -> update { copy(task = result.data, loading = false) }
                is Result.Error -> update { copy(error = result.error, loading = false) }
            }
        }.launchIn(viewModelScope)
    }
}

data class TaskDetailState(
    val loading: Boolean = false,
    val task: Task? = null,
    val error: ErrorResult? = null,
) : VmState {
    constructor() : this(true, null, null)
}

sealed interface TaskDetailIntent : VmIntent {
    data object OnInit : TaskDetailIntent
    data object OnAppeared : TaskDetailIntent
    data class OnTaskButtonTapped(val task: Task) : TaskDetailIntent
    data class OnNoteChange(val text: String) : TaskDetailIntent
    object OnCheckButtonClicked : TaskDetailIntent
}

sealed interface TaskDetailEvent : VmEvent {
    data object NavigateBackAfterChange : TaskDetailEvent
}