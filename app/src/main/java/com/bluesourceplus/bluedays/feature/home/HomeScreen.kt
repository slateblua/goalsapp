package com.bluesourceplus.bluedays.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bluesourceplus.bluedays.R
import com.bluesourceplus.bluedays.data.GoalModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenRoute(viewModel: HomeViewModel = koinViewModel(), onAddButton: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(onAddButton = onAddButton, state = state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onAddButton: () -> Unit, state: State) {
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
        when (state) {
            is State.Content -> {
                LazyColumn {
                    items(state.goals, key = { book -> book.id }) { goal ->
                        Spacer(modifier = Modifier.height(15.dp))
                        GoalCard(
                            modifier = Modifier.padding(5.dp),
                            goal = goal,
                        )
                    }
                }
            }

            State.Empty -> {
                HomeEmptyContent()
            }
        }
    }
}

@Composable
fun GoalCard(
    modifier: Modifier = Modifier,
    goal: GoalModel,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = goal.title,
                    style = MaterialTheme.typography.headlineMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = goal.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun HomeEmptyContent() {
    Column {

    }
}