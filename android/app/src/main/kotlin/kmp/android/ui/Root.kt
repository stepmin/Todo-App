package kmp.android.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.resources.compose.stringResource
import kmp.shared.base.MR
import kmp.shared.todo.presentation.navigation.TodoNavigationGraph
import kmp.shared.todo.presentation.navigation.todoNavigationNavGraph

@Composable
fun Root(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(currentRoute, navController)
        },
    ) { padding: PaddingValues ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(
                navController,
                startDestination = TodoNavigationGraph.rootPath,
            ) {
                todoNavigationNavGraph(
                    navController,
                )
            }
        }
    }
}

@Composable
fun TopBar(
    currentRoute: String?,
    navController: NavHostController,
) {
    TopAppBar(
        title = {
            if (currentRoute == TodoNavigationGraph.TaskList.route) {
                Text(text = "TODO App")
            } else if (currentRoute == TodoNavigationGraph.TaskDetail.route) {
                Text(text = "Task detail")
            }
        },
        windowInsets = WindowInsets.displayCutout,
        navigationIcon = {
            if (currentRoute == TodoNavigationGraph.TaskDetail.route) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = stringResource(MR.strings.back),
                        tint = MaterialTheme.colors.onPrimary,
                    )
                }
            } else {
                Icon(
                    painter = painterResource(kmp.android.R.drawable.ic_launcher_foreground),
                    contentDescription = "App logo",
                    tint = Color.White,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(start = 10.dp),
                )
            }
        },
    )
}