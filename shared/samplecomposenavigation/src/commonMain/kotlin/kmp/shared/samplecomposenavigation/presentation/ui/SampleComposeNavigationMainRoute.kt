package kmp.shared.samplecomposenavigation.presentation.ui

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import dev.icerock.moko.resources.compose.stringResource
import kmp.shared.base.MR
import kmp.shared.samplecomposemultiplatform.presentation.vm.TodoListEvent
import kmp.shared.samplecomposemultiplatform.presentation.vm.TodoListIntent
import kmp.shared.samplecomposemultiplatform.presentation.vm.TodoListViewModel
import kmp.shared.samplecomposenavigation.presentation.common.AppTheme
import kmp.shared.samplecomposenavigation.presentation.navigation.TodoNavigationGraph
import kmp.shared.samplecomposenavigation.presentation.navigation.composableDestination
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

internal fun NavGraphBuilder.todoListNavigationRoute(
    onShowMessage: (String) -> Unit,
    navigateToNext: () -> Unit,
) {
    composableDestination(
        destination = TodoNavigationGraph.Main,
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
    val viewModel: TodoListViewModel = koinViewModel()
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
            TodoListScreen()
//            SampleComposeMultiplatformScreen(state = state, onIntent = viewModel::onIntent)
        }
    }
}
