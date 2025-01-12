package com.bluesourceplus.bluedays.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.sharp.Timelapse
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bluesourceplus.bluedays.R
import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.feature.create.customFormat
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoute(
    viewModel: HomeViewModel = koinViewModel(),
    onAddButton: () -> Unit,
    onGoalPressed: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        onAddButton = onAddButton,
        onGoalPressed = onGoalPressed,
        state = state,
        onHomeScreenIntent = { viewModel.handleEvent(it) })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddButton: () -> Unit,
    onGoalPressed: (Int) -> Unit,
    onHomeScreenIntent: (HomeScreenIntent) -> Unit,
    state: HomeScreenState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            actions = {
                IconButton(onClick = onAddButton, modifier = Modifier.padding(5.dp)) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = stringResource(R.string.preferences_button_content_description)
                    )
                }
            }
        )
        when (state) {
            is HomeScreenState.Content -> {
                LazyColumn(modifier = Modifier.padding(horizontal = 5.dp)) {
                    items(state.goals, key = { book -> book.goalId }) { goal ->
                        Spacer(modifier = Modifier.height(15.dp))
                        GoalCard(
                            modifier = Modifier.padding(5.dp),
                            goal = goal,
                            onGoalPressed = onGoalPressed,
                            onLongPressed = {
                                onHomeScreenIntent(
                                    HomeScreenIntent.OnMarkedCompleted(
                                        it
                                    )
                                )
                            }
                        )
                    }
                }
            }

            HomeScreenState.Empty -> {
                HomeEmptyContent(onCreateButtonClicked = onAddButton)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoalCard(
    modifier: Modifier = Modifier,
    goal: GoalModel,
    onGoalPressed: (Int) -> Unit,
    onLongPressed: (GoalModel) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(onLongClick = {
                onLongPressed(goal)
            }) {
                onGoalPressed(goal.goalId)
            },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = goal.title,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Sharp.Timelapse,
                                contentDescription = "Timer icon"
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            Text(
                                text = customFormat(goal.dueDate),
                                style = MaterialTheme.typography.bodyMedium,
                            )

                            Spacer(modifier = Modifier.width(5.dp))

                            AnimatedVisibility(
                                visible = goal.completed
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Completed goal"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeEmptyContent(
    modifier: Modifier = Modifier,
    onCreateButtonClicked: () -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "You don't have anything here yet!",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 22.sp,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onCreateButtonClicked,
                content = {
                    Text(text = "Create one now!")
                },
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}