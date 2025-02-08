package kmp.shared.todo.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import kmp.shared.todo.presentation.ui.navigateToDetail
import kmp.shared.todo.presentation.ui.taskDetailNavigationRoute
import kmp.shared.todo.presentation.ui.taskListNavigationRoute

fun NavGraphBuilder.todoNavigationNavGraph(
    navHostController: NavHostController,
    onShowMessage: (String) -> Unit,
) {
    navigation(
        startDestination = TodoNavigationGraph.TaskList.route,
        route = TodoNavigationGraph.rootPath,
    ) {
        taskListNavigationRoute(
            onShowMessage = onShowMessage,
            navigateToDetail = { id, userId ->
                navHostController.navigateToDetail(
                    taskId = id,
                    userId = userId,
                )
            }
        )

        taskDetailNavigationRoute(
            onShowMessage = onShowMessage,
        )
    }
}
