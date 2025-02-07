package kmp.shared.taskDetail.presentation.navigation

object TaskDetailNavigationGraph : FeatureGraph(parent = null) {

    override val path = "taskDetailComposeNavigation"

    data object Detail : Destination(this) {
        override val routeDefinition: String = "next"
    }
}
