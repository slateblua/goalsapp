package com.bluesourceplus.bluedays

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
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
import com.bluesourceplus.bluedays.feature.aboutscreen.AboutScreenRoute
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
            PreferencesScreenRoute(back = navController::popBackStack, onAbout = { navController.navigate(ABOUT_SCREEN_ROUTE)})
        }

        appScreen(Destination.About) {
            AboutScreenRoute()
        }
    }
}

@Composable
internal fun BottomBar(navController: NavController) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        AppNavigationItem(
            rootScreen = Destination.Home,
            currentDestination = currentDestination,
            navController = navController,
            usualIcon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = stringResource(R.string.home_button_content_description)) },
            selectedIcon = { Icon(imageVector = Icons.Filled.Home, contentDescription = stringResource(R.string.preferences_button_content_description)) },
            title = "Home",
        )
        AppNavigationItem(
            rootScreen = Destination.Preferences,
            currentDestination = currentDestination,
            navController = navController,
            usualIcon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = stringResource(R.string.preferences_button_content_description)) },
            selectedIcon = { Icon(imageVector = Icons.Filled.Settings, contentDescription = stringResource(R.string.preferences_button_content_description)) },
            title = "Preferences",
        )
    }
}

@Composable
private fun RowScope.AppNavigationItem(
    rootScreen: Screen,
    currentDestination: NavDestination?,
    navController: NavController,
    usualIcon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = usualIcon,
    title: String,
) {
    val selected = currentDestination?.hierarchy?.any { it.route == rootScreen.route } == true
    NavigationBarItem(
        selected = selected,
        onClick = {
            navController.navigate(rootScreen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = if (selected) selectedIcon else usualIcon,
        alwaysShowLabel = false,
        label = {
            AnimatedVisibility(
                visible = selected,
                enter = expandVertically(
                    expandFrom = Alignment.Bottom,
                    animationSpec = spring()
                ),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = spring()
                )
            ) {
                Text(text = title, fontSize = 12.sp)
            }
        },
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

const val ABOUT_SCREEN_ROUTE = "about"

const val ABOUT_GOAL_SCREEN_ROUT = "about_goal"

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

    data object About : Screen(
        route = ABOUT_SCREEN_ROUTE,
    )

    data object AboutGoal : Screen(
        route = ABOUT_GOAL_SCREEN_ROUT,
    )
}