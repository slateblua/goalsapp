package com.bluesourceplus.bluedays.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bluesourceplus.bluedays.R

@Composable
fun HomeScreenRoute(onAddButton: () -> Unit) {
    HomeScreen(onAddButton = onAddButton)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAddButton: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background)) {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            actions = {
                IconButton(onClick = onAddButton, modifier = Modifier.padding(5.dp)) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.TwoTone.AddCircle,
                        contentDescription = stringResource(R.string.preferences_button_content_description)
                    )
                }
            }
        )
    }
}