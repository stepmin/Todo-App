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
import kmp.shared.todo.presentation.vm.TaskListViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.taskListNavigationRoute(
    onShowMessage: (String) -> Unit,
    navigateToDetail: (Int, Int) -> Unit,
) {
    composableDestination(
        destination = TodoNavigationGraph.TaskList,
    ) {
        TodoNavigationListRoute(
            onShowMessage = onShowMessage,
            navigateToDetail = navigateToDetail,
        )
    }
}

@Composable
internal fun TodoNavigationListRoute(
    onShowMessage: (String) -> Unit,
    navigateToDetail: (Int, Int) -> Unit,
) {
    val viewModel: TaskListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onIntent(TaskListIntent.OnAppeared)
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is TaskListEvent.ShowMessage -> onShowMessage(event.message)

                is TaskListEvent.NavigateToTaskDetail -> {
                    navigateToDetail(event.id, event.userId)
                }
            }
        }
    }

    AppTheme {
        TaskListScreen(
            state = state,
            onTaskChecked = {

            },
            onRowTapped = {
                navigateToDetail(it.id, it.userId)
            },
        )
    }
}
