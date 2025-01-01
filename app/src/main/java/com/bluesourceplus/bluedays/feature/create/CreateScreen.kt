package com.bluesourceplus.bluedays.feature.create

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel


@SuppressLint("ComposableNaming")
@Composable
fun <T> Flow<T>.asEffect(action: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleAwareFlow = remember(this, lifecycleOwner) {
        this.flowWithLifecycle(lifecycleOwner.lifecycle)
    }

    val currentAction by rememberUpdatedState(action)

    LaunchedEffect(this, lifecycleOwner) {
        lifecycleAwareFlow.collect { currentAction(it) }
    }
}

@Composable
fun CreateScreenRoute(createViewModel: CreateViewModel = koinViewModel(), back: () -> Unit) {
    CreateScreen(createViewModel, back)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(createViewModel: CreateViewModel, back: () -> Unit) {
    val state by createViewModel.state.collectAsStateWithLifecycle()

    val canBeCreated = remember { mutableStateOf(true) }

    createViewModel.backNavigationEvent.asEffect { back() }

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
                    if ((state as State.Content).title.isEmpty()) {
                        canBeCreated.value = false
                    } else {
                        createViewModel.handleEvent(Event.OnSaveClicked)
                    }
                },
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
                Text(text = "Save")
            }
        }
    }
}
