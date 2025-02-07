package kmp.shared.taskDetail.presentation.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import kmp.shared.taskDetail.presentation.navigation.TaskDetailNavigationGraph
import kmp.shared.taskDetail.presentation.navigation.composableDestination

internal fun NavGraphBuilder.taskDetailNavigationRoute(
    onShowMessage: (String) -> Unit,
    navigateToNext: () -> Unit,
) {
    composableDestination(
        destination = TaskDetailNavigationGraph.Detail,
    ) {
        SampleComposeNavigationMainRoute(
            onShowMessage = onShowMessage,
            navigateToNext = navigateToNext,
        )
    }
}

@Composable
internal fun SampleComposeNavigationMainRoute(
    onShowMessage: (String) -> Unit,
    navigateToNext: () -> Unit,
) {
/*    val viewModel: TodoListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onIntent(TodoListIntent.OnAppeared)
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is TodoListEvent.ShowMessage -> onShowMessage(event.message)
                TodoListEvent.NavigateBack -> TODO()
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
            TodoListScreen(state)
        }
    }*/
}
