package kmp.shared.todo.presentation.ui

import kmp.shared.todo.presentation.vm.TaskDetailEvent
import kmp.shared.todo.presentation.vm.TaskDetailIntent
import kmp.shared.todo.presentation.vm.TaskDetailViewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kmp.shared.todo.presentation.common.AppTheme
import kmp.shared.todo.presentation.navigation.TodoNavigationGraph
import kmp.shared.todo.presentation.navigation.composableDestination
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

internal fun NavController.navigateToDetail(taskId: Int, userId: Int) {
    val route = TodoNavigationGraph.TaskDetail(taskId, userId)
    navigate(route)
}

internal fun NavGraphBuilder.taskDetailNavigationRoute(
    navigateToBack: () -> Unit,
) {
    val destination = TodoNavigationGraph.TaskDetail
    composableDestination(
        destination = destination,
    ) { it: NavBackStackEntry ->
        val id = it.arguments?.getInt("id")
        val userId = it.arguments?.getInt("userId")
        SavedStateHandle(mapOf("id" to id, "userId" to userId))
        TaskDetailRoute(
            navigateToBack = navigateToBack,
        )
    }
}

@Composable
internal fun TaskDetailRoute(
    navigateToBack: () -> Unit,
) {
    val viewModel: TaskDetailViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onIntent(TaskDetailIntent.OnAppeared)
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                TaskDetailEvent.NavigateBack -> {
                    navigateToBack()
                }
            }
        }
    }

    AppTheme {
        TaskDetailScreen(
            state = state,
            markTask = { task ->
                viewModel.onIntent(TaskDetailIntent.OnTaskButtonTapped(task))
            },
            onNoteChange = {
                viewModel.onIntent(TaskDetailIntent.OnNoteChange(it))
            },
        )
    }
}
