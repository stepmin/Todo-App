package kmp.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kmp.shared.todo.presentation.navigation.TodoNavigationGraph
import kmp.shared.todo.presentation.navigation.todoNavigationNavGraph

@Composable
fun Root(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
    ) { padding: PaddingValues ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController,
                startDestination = TodoNavigationGraph.rootPath,
            ) {
                todoNavigationNavGraph(
                    navController
                )
            }
        }
    }
}