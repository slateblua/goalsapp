package com.bluesourceplus.bluedays

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bluesourceplus.bluedays.feature.create.CreateScreenRoute
import com.bluesourceplus.bluedays.feature.home.HomeScreenRoute
import com.bluesourceplus.bluedays.feature.preferences.PreferencesScreenRoute

@Composable
fun BlueDaysScreensHost(
    navController: NavHostController,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Destination.Home.route,
        modifier =
        Modifier
            .padding(padding)
            .fillMaxSize(),
    ) {
        appScreen(Destination.Home) {
            HomeScreenRoute(onAddButton = { navController.navigate(CREATE_SCREEN_ROUTE) })
        }

        appScreen(Destination.Preferences) {
            PreferencesScreenRoute()
        }

        appScreen(Destination.Create) {
            CreateScreenRoute()
        }
    }
}

@Composable
internal fun BottomBar(navController: NavController) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 40.dp)
            .background(color = MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(60.dp)))
    {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        AppNavigationItem(
            rootScreen = Destination.Home,
            currentDestination = currentDestination,
            navController = navController,
            content = { Icon(imageVector = Icons.Default.Home, contentDescription = stringResource(R.string.home_button_content_description), tint = MaterialTheme.colorScheme.onSecondaryContainer) },
        )
        AppNavigationItem(
            rootScreen = Destination.Preferences,
            currentDestination = currentDestination,
            navController = navController,
            content = { Icon(imageVector = Icons.Default.Settings, contentDescription = stringResource(R.string.preferences_button_content_description), tint = MaterialTheme.colorScheme.onSecondaryContainer) },
        )
    }
}

@Composable
private fun RowScope.AppNavigationItem(
    rootScreen: Screen,
    currentDestination: NavDestination?,
    navController: NavController,
    content: @Composable () -> Unit,
) {
    NavigationBarItem(
        selected = currentDestination?.hierarchy?.any { it.route == rootScreen.route } == true,
        onClick = {
            navController.navigate(rootScreen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = content,
    )
}

sealed class Screen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
)

fun NavGraphBuilder.appScreen(
    screen: Screen,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = screen.route,
        arguments = screen.arguments,
        content = content,
    )
}

const val CREATE_SCREEN_ROUTE = "create"
const val HOME_SCREEN_ROUTE = "home"
const val PREFERENCES_SCREEN_ROUTE = "preferences"

object Destination {
    data object Create : Screen(
        route = CREATE_SCREEN_ROUTE,
    )

    data object Home : Screen(
        route = HOME_SCREEN_ROUTE,
    )

    data object Preferences : Screen(
        route = PREFERENCES_SCREEN_ROUTE,
    )
}