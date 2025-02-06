package kmp.shared.samplecomposemultiplatform.presentation.vm

import kmp.shared.base.ErrorResult
import kmp.shared.samplecomposemultiplatform.domain.usecase.GetTasksUseCase
import kmp.shared.samplesharedviewmodel.base.vm.BaseViewModel
import kmp.shared.samplesharedviewmodel.base.vm.VmEvent
import kmp.shared.samplesharedviewmodel.base.vm.VmIntent
import kmp.shared.samplesharedviewmodel.base.vm.VmState
import kmp.shared.base.Result
import kmp.shared.samplecomposemultiplatform.domain.model.Task

class TodoListViewModel(
    private val getTasksData: GetTasksUseCase,
) : BaseViewModel<TodoListState, TodoListIntent, TodoListEvent>(TodoListState()) {

    override suspend fun applyIntent(intent: TodoListIntent) {
        when (intent) {
            TodoListIntent.OnAppeared -> {
                loadData()
            }
            TodoListIntent.OnBackTapped -> TODO()
            TodoListIntent.OnButtonTapped -> TODO()
        }
    }

    private suspend fun loadData() {
        update { copy(loading = true) }
        when (val result = getTasksData()) {
            is Result.Success -> update { copy(tasks = result.data, loading = false) }
            is Result.Error -> update { copy(error = result.error, loading = false) }
        }

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
    data object OnBackTapped : TodoListIntent
}

sealed interface TodoListEvent : VmEvent {
    data class ShowMessage(val message: String) : TodoListEvent
    data object NavigateBack : TodoListEvent
}