package kmp.android.navigation

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kmp.shared.base.MR
import kmp.shared.taskList.presentation.navigation.TodoNavigationGraph

enum class NavBarFeature(val route: String, val titleRes: StringDesc) {
    SampleComposeNavigation(TodoNavigationGraph.rootPath, MR.strings.bottom_bar_item_4.desc()),
}
