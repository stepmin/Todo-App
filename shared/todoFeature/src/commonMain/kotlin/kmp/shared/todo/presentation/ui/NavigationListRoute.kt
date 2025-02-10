package kmp.shared.todo.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
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
    navigateToDetail: (Int, Int) -> Unit,
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
    navigateToDetail: (Int, Int) -> Unit,
) {
    val viewModel: TaskListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onIntent(TaskListIntent.OnBackFromDetail)
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is TaskListEvent.NavigateToTaskDetail -> {
                    navigateToDetail(event.taskId, event.userId)
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
                navigateToDetail(it.id, it.userId)
            },
        )
    }
}
