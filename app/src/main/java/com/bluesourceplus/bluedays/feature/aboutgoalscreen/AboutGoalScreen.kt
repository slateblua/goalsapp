package com.bluesourceplus.bluedays.feature.aboutgoalscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutGoalRoute(
    aboutGoalViewModel: AboutGoalViewModel = koinViewModel(),
    goalId: Int,
    back: () -> Unit
) {
    val state by aboutGoalViewModel.state.collectAsStateWithLifecycle()

    AboutGoalScreen(
        state = state,
        aboutGoalViewModel = aboutGoalViewModel,
        goalId = goalId,
        back = back,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@SuppressWarnings("unused")
fun AboutGoalScreen(state: State, aboutGoalViewModel: AboutGoalViewModel, goalId: Int, back: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)) {
        CenterAlignedTopAppBar(
            title = { Text(text = "About goal")},
            navigationIcon = {
                IconButton(onClick = back) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
        // TODO: Refactor this to not leak internal methods from a viewModel 
        LaunchedEffect(Unit) {
            aboutGoalViewModel.loadGoal(goalId)
        }

        when (state) {
            is State.Content -> {

            }
        }
    }
}