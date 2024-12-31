package com.bluesourceplus.bluedays.feature.preferences

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PreferencesScreenRoute() {
    PreferencesScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferencesScreen() {
    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        CenterAlignedTopAppBar(title = { Text(text = "Preferences") })
    }
}