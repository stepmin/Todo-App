import kmp.shared.base.ErrorResult
import kmp.shared.base.Result
import kmp.shared.todo.base.vm.BaseViewModel
import kmp.shared.todo.base.vm.VmEvent
import kmp.shared.todo.base.vm.VmIntent
import kmp.shared.todo.base.vm.VmState
import kmp.shared.todo.domain.model.TaskDetail
import kmp.shared.todo.domain.usecase.GetTaskDetailUseCase

class TaskDetailViewModel(
    private val getTaskDetailUseCase: GetTaskDetailUseCase,
//    savedStateHandle: SavedStateHandle,
) : BaseViewModel<TaskDetailState, TaskDetailIntent, TaskDetailEvent>(TaskDetailState()) {

    override suspend fun applyIntent(intent: TaskDetailIntent) {
        when (intent) {
            TaskDetailIntent.OnAppeared -> {
                loadData(Pair(1, 1))
            }
        }
    }

    private suspend fun loadData(input: Pair<Int, Int>) {
        update { copy(loading = true) }
        when (val taskDetailUseCase = getTaskDetailUseCase(input)) {
            is Result.Success -> {
                update { copy(taskDetail = taskDetailUseCase.data, loading = false) }
            }

            is Result.Error -> {
                update { copy(error = taskDetailUseCase.error, loading = false) }
            }
        }
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
    data object OnAppeared : TaskDetailIntent
}

sealed interface TaskDetailEvent : VmEvent {
    data class ShowMessage(val message: String) : TaskDetailEvent
    data object NavigateBack : TaskDetailEvent
}