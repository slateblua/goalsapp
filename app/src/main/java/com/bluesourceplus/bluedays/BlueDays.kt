package com.bluesourceplus.bluedays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bluesourceplus.bluedays.composables.theme.BluedaysTheme
import org.koin.compose.KoinContext

class BlueDays : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BluedaysTheme {
                KoinContext {
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val shouldShowBottomBar = shouldShowBottomBar(navController)
                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(
                                visible = shouldShowBottomBar,
                                enter = expandVertically(
                                    expandFrom = Alignment.Bottom,
                                    animationSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioLowBouncy),
                                ),
                                exit = shrinkVertically(
                                    shrinkTowards = Alignment.Top,
                                    animationSpec = spring(stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioLowBouncy),
                                )
                            ) {
                                BottomBar(navController)
                            }
                        },
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                        modifier = Modifier.fillMaxSize(),
                        contentWindowInsets = WindowInsets(top = 0.dp),
                    ) { innerPadding ->
                        BlueDaysScreensHost(navController, innerPadding)
                    }
                }
            }
        }
    }
}

@Composable
fun shouldShowBottomBar(navController: NavHostController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: return false

    return ShowNavBarScreens.screens.any { it.route == currentRoute && it.hasBottomBar }
}
