package com.bluesourceplus.bluedays.feature.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun PreferencesScreenRoute(preferencesViewModel: PreferencesViewModel = koinViewModel(), back: () -> Unit, onAbout: () -> Unit) {
    PreferencesScreen(
        preferencesViewModel = preferencesViewModel,
        back = back,
        onAbout = onAbout
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Suppress("unused")
fun PreferencesScreen(preferencesViewModel: PreferencesViewModel, back: () -> Unit, onAbout: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Preferences")},
            navigationIcon = {
                IconButton(onClick = back) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            actions = {
                IconButton(onClick = onAbout) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "Back")
                }
            }
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Made by @slateblua")
        }
    }
}
