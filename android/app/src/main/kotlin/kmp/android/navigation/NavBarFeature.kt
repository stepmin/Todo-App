package kmp.android.navigation

import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import kmp.shared.base.MR
import kmp.shared.samplecomposenavigation.presentation.navigation.TodoNavigationGraph

enum class NavBarFeature(val route: String, val titleRes: StringDesc) {
//    Sample(SampleGraph.rootPath, MR.strings.bottom_bar_item_1.desc()),
//    SampleSharedViewModel(SampleSharedViewModelGraph.rootPath, MR.strings.bottom_bar_item_2.desc()),
//    SampleComposeMultiplatform(SampleComposeMultiplatformGraph.rootPath, MR.strings.bottom_bar_item_3.desc()),
    SampleComposeNavigation(TodoNavigationGraph.rootPath, MR.strings.bottom_bar_item_4.desc()),
}
