package com.bluesourceplus.bluedays.feature.aboutgoalscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
        state = state,
        aboutGoalViewModel = aboutGoalViewModel,
        goalId = goalId,
        back = back,
        onEditPressed = onEditPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutGoalScreen(state: State, aboutGoalViewModel: AboutGoalViewModel, goalId: Int, back: () -> Unit, onEditPressed: (Int) -> Unit) {
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

        LaunchedEffect(Unit) {
            aboutGoalViewModel.handleEvent(Event.LoadGoal(goalId))
        }

        when (state) {
            is State.Content -> {
                Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                    Column {
                        Row {
                            Icon(imageVector = Icons.Outlined.FlashOn, contentDescription = "Goal Icon")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = state.title)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Icon(imageVector = Icons.Outlined.Description, contentDescription = "Calendar Icon")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = state.description)
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Icon(imageVector = Icons.Outlined.CalendarMonth, contentDescription = "Calendar Icon")
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = customFormat(state.dueDate))
                        }
                    }
                    Button(
                        modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { onEditPressed(state.id) },
                    ) {
                        Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Edit Icon")
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(text = "Edit")
                    }
                }
            }
        }
    }
}