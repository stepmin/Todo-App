package kmp.shared.taskList.presentation.ui

import TaskDetailEvent
import TaskDetailIntent
import TaskDetailScreen
import TaskDetailViewModel
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.icerock.moko.resources.compose.stringResource
import kmp.shared.base.MR
import kmp.shared.taskList.presentation.common.AppTheme
import kmp.shared.taskList.presentation.navigation.TodoNavigationGraph
import kmp.shared.taskList.presentation.navigation.composableDestination
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

internal fun NavController.navigateToDetail(taskId: Int, userId: Int) {
    val route = TodoNavigationGraph.TaskDetail(taskId, userId)
    navigate(route)
}

internal fun NavGraphBuilder.taskDetailNavigationRoute(
    onShowMessage: (String) -> Unit,
) {
    val destination = TodoNavigationGraph.TaskDetail
    composableDestination(
        destination = destination,
    ) { it: NavBackStackEntry ->
        val id = it.arguments?.getInt("id")
        val userId = it.arguments?.getInt("userId")
        SavedStateHandle(mapOf("id" to id, "userId" to userId))
        TaskDetailRoute(
            onShowMessage = onShowMessage,
        )
    }
}

@Composable
internal fun TaskDetailRoute(
    onShowMessage: (String) -> Unit,
) {
    val viewModel: TaskDetailViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onIntent(TaskDetailIntent.OnAppeared)
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                TaskDetailEvent.NavigateBack -> TODO()
                is TaskDetailEvent.ShowMessage -> TODO()
            }
        }
    }

    AppTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = stringResource(MR.strings.bottom_bar_item_4),
                )
            },
        ) {
            TaskDetailScreen(state)
        }
    }
}
