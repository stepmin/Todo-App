package kmp.shared.samplecomposenavigation.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import kmp.shared.samplecomposenavigation.presentation.ui.navigateToDetail
import kmp.shared.samplecomposenavigation.presentation.ui.todoListNavigationDetailRoute
import kmp.shared.samplecomposenavigation.presentation.ui.todoListNavigationRoute

fun NavGraphBuilder.todoNavigationNavGraph(
    navHostController: NavHostController,
    onShowMessage: (String) -> Unit,
) {
    navigation(
        startDestination = TodoNavigationGraph.Main.route,
        route = TodoNavigationGraph.rootPath,
    ) {
        todoListNavigationRoute(
            onShowMessage = onShowMessage,
            navigateToNext = { navHostController.navigateToDetail() },
        )

        todoListNavigationDetailRoute(
            onShowMessage = onShowMessage,
            navigateToBack = { navHostController.popBackStack() },
        )
    }
}
