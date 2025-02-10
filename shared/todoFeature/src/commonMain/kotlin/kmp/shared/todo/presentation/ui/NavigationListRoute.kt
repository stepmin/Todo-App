package kmp.shared.todo.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import kmp.shared.todo.domain.usecase.TaskId
import kmp.shared.todo.presentation.common.AppTheme
import kmp.shared.todo.presentation.navigation.TodoNavigationGraph
import kmp.shared.todo.presentation.navigation.composableDestination
import kmp.shared.todo.presentation.vm.TaskListEvent
import kmp.shared.todo.presentation.vm.TaskListIntent
import kmp.shared.todo.presentation.vm.TaskListIntent.OnTaskCheckTapped
import kmp.shared.todo.presentation.vm.TaskListViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.taskListNavigationRoute(
    navigateToDetail: (TaskId) -> Unit,
) {
    composableDestination(
        destination = TodoNavigationGraph.TaskList,
    ) {
        TaskListRoute(
            navigateToDetail = navigateToDetail,
        )
    }
}

@Composable
internal fun TaskListRoute(
    navigateToDetail: (TaskId) -> Unit
) {
    val viewModel: TaskListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var dataLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = viewModel) {
        if (dataLoaded) {
            viewModel.onIntent(TaskListIntent.OnAppeared)
        } else {
            viewModel.onIntent(TaskListIntent.OnInit)
            dataLoaded = true
        }

        viewModel.events.collectLatest { event ->
            when (event) {
                is TaskListEvent.NavigateToTaskDetail -> {
                    navigateToDetail(event.taskId)
                }
            }
        }
    }

    AppTheme {
        TaskListScreen(
            state = state,
            onTaskChecked = {
                viewModel.onIntent(OnTaskCheckTapped(it))
            },
            onRowTapped = {
                viewModel.onIntent(TaskListIntent.OnRowTapped(it.id))
            },
        )
    }
}
