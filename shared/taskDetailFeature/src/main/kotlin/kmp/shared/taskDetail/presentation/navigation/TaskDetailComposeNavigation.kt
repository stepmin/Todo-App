package kmp.shared.taskDetail.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import kmp.shared.taskDetail.presentation.ui.taskDetailNavigationRoute

fun NavGraphBuilder.taskDetailNavigationNavGraph(
    navHostController: NavHostController,
    onShowMessage: (String) -> Unit,
) {
    navigation(
        startDestination = TaskDetailNavigationGraph.Detail.route,
        route = TaskDetailNavigationGraph.rootPath,
    ) {
        taskDetailNavigationRoute(
            onShowMessage = onShowMessage,
            navigateToNext = {
//                navHostController.navigateToDetail()
            },
        )
    }
}
