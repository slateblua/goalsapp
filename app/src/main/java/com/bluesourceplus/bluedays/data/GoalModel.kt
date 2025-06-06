package com.bluesourceplus.bluedays.data

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate

@Immutable
data class GoalModel(
    val goalId: Int = 0,
    val title: String,
    val description: String,
    val dueDate: LocalDate,
    val completed: Boolean,
)