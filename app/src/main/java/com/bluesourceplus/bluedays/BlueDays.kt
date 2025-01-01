package com.bluesourceplus.bluedays

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bluesourceplus.bluedays.composables.BluedaysTheme
import com.bluesourceplus.bluedays.data.database.module.dataModule
import com.bluesourceplus.bluedays.feature.create.module.createModule
import com.bluesourceplus.bluedays.feature.home.module.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinContext
import org.koin.core.context.startKoin

class BlueDays : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            androidContext(this@BlueDays)
            androidLogger()
            modules(homeModule, dataModule, createModule)
        }

        setContent {
            BluedaysTheme {
                KoinContext {
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    Scaffold(
                        bottomBar = { BottomBar(navController) },
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
