package kmp.shared.taskList.presentation.navigation

object TodoNavigationGraph : FeatureGraph(parent = null) {

    override val path = "todoComposeNavigation"

    data object Main : Destination(this) {
        override val routeDefinition: String = "main"
    }

    data object Detail : Destination(this) {
        override val routeDefinition: String = "next"
    }
}
