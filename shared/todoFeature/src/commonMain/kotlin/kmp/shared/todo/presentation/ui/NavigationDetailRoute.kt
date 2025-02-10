package kmp.shared.todo.presentation.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.icerock.moko.resources.compose.stringResource
import kmp.shared.base.MR
import kmp.shared.todo.presentation.common.AppTheme
import kmp.shared.todo.presentation.navigation.TodoNavigationGraph
import kmp.shared.todo.presentation.navigation.composableDestination
import kmp.shared.todo.presentation.ui.components.TopBar
import kmp.shared.todo.presentation.vm.TaskDetailEvent
import kmp.shared.todo.presentation.vm.TaskDetailIntent
import kmp.shared.todo.presentation.vm.TaskDetailViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

internal fun NavController.navigateToDetail(taskId: Int) {
    val route = TodoNavigationGraph.TaskDetail(taskId)
    navigate(route)
}

internal fun NavGraphBuilder.taskDetailNavigationRoute(
    navigateToBack: () -> Unit,
) {
    val destination = TodoNavigationGraph.TaskDetail
    composableDestination(
        destination = destination,
    ) { it: NavBackStackEntry ->
        val id = it.arguments?.getInt("taskId")
        SavedStateHandle(mapOf("taskId" to id))
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
    var loaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        if (loaded) {
            viewModel.onIntent(TaskDetailIntent.OnAppeared)
        } else {
            viewModel.onIntent(TaskDetailIntent.OnInit)
            loaded = true
        }

        viewModel.events.collectLatest { event ->
            when (event) {
                TaskDetailEvent.NavigateBackAfterChange -> {
                    navigateToBack()
                }
            }
        }
    }

    AppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(MR.strings.top_bar_label),
                    onBackClick = {
                        navigateToBack()
                    },
                    actionIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onIntent(TaskDetailIntent.OnCheckButtonClicked)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                    },
                )
            },
        ) {
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
}