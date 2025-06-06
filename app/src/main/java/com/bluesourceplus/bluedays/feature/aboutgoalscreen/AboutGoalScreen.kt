package com.bluesourceplus.bluedays.feature.aboutgoalscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bluesourceplus.bluedays.feature.create.customFormat
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutGoalRoute(
    aboutGoalViewModel: AboutGoalViewModel = koinViewModel(),
    goalId: Int,
    back: () -> Unit,
    onEditPressed: (Int) -> Unit
) {
    val state by aboutGoalViewModel.state.collectAsStateWithLifecycle()

    AboutGoalScreen(
        goalId = goalId,
        state = state,
        aboutGoalViewModel = aboutGoalViewModel,
        back = back,
        onEditPressed = onEditPressed,
        onAboutGoalIntent = { aboutGoalViewModel.handleEvent(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutGoalScreen(
    goalId: Int,
    state: AboutGoalState,
    aboutGoalViewModel: AboutGoalViewModel,
    back: () -> Unit,
    onEditPressed: (Int) -> Unit,
    onAboutGoalIntent: (AboutGoalIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = "About task") },
            navigationIcon = {
                IconButton(onClick = back) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
        )

        LaunchedEffect(Unit) {
            aboutGoalViewModel.handleEvent(AboutGoalIntent.LoadGoal(goalId))
        }

        LaunchedEffect(Unit) {
            aboutGoalViewModel.sideEffect.collect { effect ->
                when (effect) {
                    AboutGoalEffect.GoalDeleted -> {
                        back()
                    }
                }
            }
        }

        when (state) {
            is AboutGoalState.Content -> {
                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, top = 25.dp, end = 20.dp, bottom = 30.dp)
                        .fillMaxSize()
                ) {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "Title", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = state.title)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "Description", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = state.description)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(text = "Due Date", style = MaterialTheme.typography.titleLarge)
                            Spacer(modifier = Modifier.width(15.dp))
                            Text(text = customFormat(state.dueDate))
                        }
                    }

                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Button(
                                modifier = Modifier.weight(0.5f).height(50.dp),
                                shape = RoundedCornerShape(15.dp),
                                onClick = { onEditPressed(state.id) },
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit Icon"
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(
                                modifier = Modifier.weight(0.5f).height(50.dp),
                                shape = RoundedCornerShape(15.dp),
                                onClick = { onAboutGoalIntent(AboutGoalIntent.DeleteGoal(state.id)) }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.DeleteOutline,
                                    contentDescription = "Delete Icon"
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}