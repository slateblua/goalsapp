package com.bluesourceplus.bluedays.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.sharp.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.feature.create.customFormat

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
                                color = MaterialTheme.colorScheme.surfaceBright,
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