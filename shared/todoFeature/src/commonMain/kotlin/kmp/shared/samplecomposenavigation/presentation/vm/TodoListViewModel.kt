package kmp.shared.samplecomposenavigation.presentation.vm

import androidx.lifecycle.viewModelScope
import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.samplecomposenavigation.base.vm.BaseViewModel
import kmp.shared.samplecomposenavigation.base.vm.VmEvent
import kmp.shared.samplecomposenavigation.base.vm.VmIntent
import kmp.shared.samplecomposenavigation.base.vm.VmState
import kmp.shared.samplecomposenavigation.domain.model.Task
import kmp.shared.samplecomposenavigation.domain.usecase.GetTasksUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TodoListViewModel(
    private val getTasksData: GetTasksUseCase,
) : BaseViewModel<TodoListState, TodoListIntent, TodoListEvent>(TodoListState()) {

    override suspend fun applyIntent(intent: TodoListIntent) {
        when (intent) {
            TodoListIntent.OnAppeared -> {
                loadData()
            }
            TodoListIntent.OnButtonTapped -> TODO()
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

data class TodoListState(
    val loading: Boolean = false,
    val tasks: List<Task>? = null,
    val error: ErrorResult? = null,
) : VmState {
    constructor() : this(true, null, null)
}

sealed interface TodoListIntent : VmIntent {
    data object OnAppeared : TodoListIntent
    data object OnButtonTapped : TodoListIntent
}

sealed interface TodoListEvent : VmEvent {
    data class ShowMessage(val message: String) : TodoListEvent
    data object NavigateBack : TodoListEvent
}