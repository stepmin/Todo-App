package kmp.shared.taskList.presentation.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import dev.icerock.moko.resources.compose.stringResource
import kmp.shared.base.MR
import kmp.shared.taskList.presentation.common.AppTheme
import kmp.shared.taskList.presentation.navigation.TodoNavigationGraph
import kmp.shared.taskList.presentation.navigation.composableDestination
import kmp.shared.taskList.presentation.vm.TaskListEvent
import kmp.shared.taskList.presentation.vm.TaskListIntent
import kmp.shared.taskList.presentation.vm.TaskListViewModel
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
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = stringResource(MR.strings.bottom_bar_item_4),
                )
            },
        ) {
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
}
