package com.bluesourceplus.bluedays.feature.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.datetime.LocalDate
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun CreateScreenRoute(createViewModel: CreateViewModel = koinViewModel(), back: () -> Unit) {
    CreateScreen(createViewModel, back)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(createViewModel: CreateViewModel, back: () -> Unit) {
    val state by createViewModel.state.collectAsStateWithLifecycle()

    val canBeCreated = remember { mutableStateOf(true) }

    val shouldShowDate = remember { mutableStateOf(false) }

    val dateState = rememberDatePickerState()

    // Collect side effects
    LaunchedEffect(Unit) {
        createViewModel.sideEffect.collect { effect ->
            when (effect) {
                Effect.TaskSaved -> {
                    // empty for now
                }
                Effect.NavigateUp -> {
                    back()
                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background).padding()) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Create")},
            navigationIcon = {
                IconButton(onClick = back) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Column (modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            OutlinedTextField(
                placeholder = { Text("A measurable, reachable goal") },
                modifier = Modifier.fillMaxWidth(),
                value = (state as State.Content).title,
                onValueChange = {
                    createViewModel.handleEvent(Event.OnTitleChanged(it))
                },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (!canBeCreated.value) {
                Text(text = "Please enter a title", color = MaterialTheme.colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                placeholder = { Text("Why do you want to achieve it?") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                value = (state as State.Content).description,
                onValueChange = {
                    createViewModel.handleEvent(Event.OnDescriptionChanged(it))
                },
                singleLine = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    shouldShowDate.value = true
                },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
                Text(text = customFormat((state as State.Content).dueDate))
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if ((state as State.Content).title.isEmpty()) {
                        canBeCreated.value = false
                    } else {
                        createViewModel.handleEvent(Event.OnSaveClicked)
                    }
                },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
                Text(text = "Save")
            }

            val selectedDate = dateState.selectedDateMillis?.let {
                LocalDate.fromEpochDays((it / (24 * 60 * 60 * 1000)).toInt())
            }

            if (shouldShowDate.value) {
                DatePickerDialog(
                    onDismissRequest = {
                        shouldShowDate.value = false
                    },
                    confirmButton = {
                        Button(onClick = {
                            shouldShowDate.value = false
                            selectedDate?.let {
                                createViewModel.handleEvent(Event.OnDueDateChanged(dueDate = selectedDate))
                            }
                        }, modifier = Modifier.fillMaxWidth().padding(20.dp), shape = RoundedCornerShape(10.dp)) {
                            Text(text = "Confirm")
                        }
                    },
                ) {
                    DatePicker(state = dateState)
                }
            }
        }
    }
}

fun customFormat(date: LocalDate): String {
    val day = date.dayOfMonth
    val month =
        date.month.name.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
    val year = date.year
    return "$day $month $year"
}
