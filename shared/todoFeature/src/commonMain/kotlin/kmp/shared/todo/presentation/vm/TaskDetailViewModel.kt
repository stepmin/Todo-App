package kmp.shared.todo.presentation.vm

import androidx.lifecycle.viewModelScope
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.todo.base.vm.BaseViewModel
import kmp.shared.todo.base.vm.VmEvent
import kmp.shared.todo.base.vm.VmIntent
import kmp.shared.todo.base.vm.VmState
import kmp.shared.todo.domain.model.TaskDetailRequest
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.usecase.GetTaskDetailUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TaskDetailViewModel(
    private val getTaskDetailUseCase: GetTaskDetailUseCase,
//    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskDetailState, TaskDetailIntent, TaskDetailEvent>(TaskDetailState()) {

    init {
        onIntent(TaskDetailIntent.OnInit)
    }

    override suspend fun applyIntent(intent: TaskDetailIntent) {
        when (intent) {
            TaskDetailIntent.OnInit -> {
                loadData(TaskDetailRequest(5, 1))
            }

            TaskDetailIntent.OnAppeared -> {
            }

            is TaskDetailIntent.OnCompletedTapped -> {
                markAsCompleted(intent.id)
            }
        }
    }

    private suspend fun loadData(input: TaskDetailRequest) {
        update { copy(loading = true) }
        getTaskDetailUseCase(input).onEach { result ->
            when (result) {
                is Result.Success -> update { copy(taskDetail = result.data, loading = false) }
                is Result.Error -> update { copy(error = result.error, loading = false) }
            }

        }.launchIn(viewModelScope)
    }

    private fun markAsCompleted(i: Int) {
        TODO("Not yet implemented")
    }

}

data class TaskDetailState(
    val loading: Boolean = false,
    val taskDetail: TaskDetail? = null,
    val error: ErrorResult? = null,
) : VmState {
    constructor() : this(true, null, null)
}

sealed interface TaskDetailIntent : VmIntent {
    data object OnInit : TaskDetailIntent
    data object OnAppeared : TaskDetailIntent
    data class OnCompletedTapped(val id: Int) : TaskDetailIntent
}

sealed interface TaskDetailEvent : VmEvent {
    data class ShowMessage(val message: String) : TaskDetailEvent
    data object NavigateBack : TaskDetailEvent
}