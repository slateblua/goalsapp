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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateScreenRoute() {
    CreateScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen() {
    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.background).padding()) {
        CenterAlignedTopAppBar(
            title = { Text(text = "Create") }
        )
        Column (modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            OutlinedTextField(
                placeholder = { Text("Placeholder") },
                modifier = Modifier.fillMaxWidth(),
                value = "",
                onValueChange = { },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                placeholder = { Text("Placeholder") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                value = "",
                onValueChange = { },
                singleLine = false
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)) {
                Text(text = "Save")
            }
        }
    }
}
