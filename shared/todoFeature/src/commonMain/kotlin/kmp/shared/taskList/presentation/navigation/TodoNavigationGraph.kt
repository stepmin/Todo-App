package kmp.shared.taskList.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

object TodoNavigationGraph : FeatureGraph(parent = null) {

    override val path = "todoComposeNavigation"

    data object TaskList : Destination(this) {
        override val routeDefinition: String = "task_list"
    }

    data object TaskDetail : Destination(this) {
        
        override val routeDefinition: String = "task_detail"

        //TODO-navigation
        override val arguments: List<NamedNavArgument> = listOf(
            navArgument("id") { type = NavType.IntType },
            navArgument("userId") { type = NavType.IntType },
        )
    }
}
