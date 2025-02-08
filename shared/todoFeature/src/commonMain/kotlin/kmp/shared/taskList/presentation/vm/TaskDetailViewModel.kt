import androidx.lifecycle.SavedStateHandle
import kmp.shared.base.ErrorResult
import kmp.shared.taskList.base.vm.BaseViewModel
import kmp.shared.taskList.base.vm.VmEvent
import kmp.shared.taskList.base.vm.VmIntent
import kmp.shared.taskList.base.vm.VmState
import kmp.shared.taskList.domain.model.Task
import kmp.shared.taskList.domain.usecase.GetTasksUseCase

class TaskDetailViewModel(
    private val getTasksData: GetTasksUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskDetailState, TaskDetailIntent, TaskDetailEvent>(TaskDetailState()) {

    init {
        val id = savedStateHandle.get<Int>("id")
        val userId = savedStateHandle.get<Int>("userId")
    }

    override suspend fun applyIntent(intent: TaskDetailIntent) {
        when (intent) {
            TaskDetailIntent.OnAppeared -> {
                loadData()
            }
            TaskDetailIntent.OnButtonTapped -> TODO()
        }
    }

    private suspend fun loadData() {
/*        update { copy(loading = true) }
        getTasksData.invoke().onEach { result ->
            when (result) {
                is kmp.shared.base.Result.Success -> update { copy(tasks = result.data, loading = false) }
                is Result.Error -> update { copy(error = result.error, loading = false) }
            }
        }.launchIn(viewModelScope)*/
    }

}

data class TaskDetailState(
    val loading: Boolean = false,
    val tasks: List<Task>? = null,
    val error: ErrorResult? = null,
) : VmState {
    constructor() : this(true, null, null)
}

sealed interface TaskDetailIntent : VmIntent {
    data object OnAppeared : TaskDetailIntent
    data object OnButtonTapped : TaskDetailIntent
}

sealed interface TaskDetailEvent : VmEvent {
    data class ShowMessage(val message: String) : TaskDetailEvent
    data object NavigateBack : TaskDetailEvent
}