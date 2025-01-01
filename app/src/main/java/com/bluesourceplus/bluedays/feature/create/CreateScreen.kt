package com.bluesourceplus.bluedays.feature.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateScreenRoute(createViewModel: CreateViewModel = koinViewModel(), back: () -> Unit) {
    CreateScreen(createViewModel, back)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(createViewModel: CreateViewModel, back: () -> Unit) {
    val state by createViewModel.state.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background).padding()) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Create") }
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
                    createViewModel.handleEvent(Event.OnSaveClicked)
                    back()
                },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
                Text(text = "Save")
            }
        }
    }
}
