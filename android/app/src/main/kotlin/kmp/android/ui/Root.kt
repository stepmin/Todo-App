package kmp.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.resources.compose.stringResource
import kmp.shared.base.MR
import kmp.shared.todo.presentation.navigation.TodoNavigationGraph
import kmp.shared.todo.presentation.navigation.todoNavigationNavGraph
import kmp.shared.todo.presentation.ui.CenterAlignedTopAppBar

@Composable
fun Root(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = stringResource(MR.strings.bottom_bar_item_4),
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController,
                startDestination = TodoNavigationGraph.rootPath,
            ) {
                todoNavigationNavGraph(
                    navController,
                    onShowMessage = {

                    },
                )
            }
        }
    }
}
