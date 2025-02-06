package kmp.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.resources.compose.localized
import kmp.android.navigation.NavBarFeature
import kmp.android.shared.style.Elevation
import kmp.shared.samplecomposenavigation.presentation.navigation.TodoNavigationGraph
import kmp.shared.samplecomposenavigation.presentation.navigation.todoNavigationNavGraph

@Composable
fun Root(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomBar(navController) },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController,
                startDestination = TodoNavigationGraph.rootPath,
            ) {
                todoNavigationNavGraph(navController, onShowMessage = {

                })
            }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        elevation = Elevation.huge,
        color = MaterialTheme.colors.primarySurface,
        modifier = modifier,
    ) {
        BottomNavigation(
            Modifier.navigationBarsPadding(),
            elevation = 0.dp,
        ) {
            NavBarFeature.entries.forEach { screen ->
                BottomNavigationItem(
                    icon = {
                        when (screen) {
                            /*NavBarFeature.Sample -> Icon(Icons.Filled.Person, "")
                            NavBarFeature.SampleSharedViewModel -> Icon(
                                Icons.Filled.AccountCircle,
                                "",
                            )

                            NavBarFeature.SampleComposeMultiplatform -> Icon(
                                Icons.Filled.AccountBox,
                                "",
                            )*/

                            NavBarFeature.SampleComposeNavigation -> Icon(
                                Icons.Filled.Face,
                                "",
                            )
                        }
                    },
                    label = { Text(screen.titleRes.localized()) },
                    selected = currentRoute?.startsWith(screen.route + "/") ?: false,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    },
                )
            }
        }
    }
}
