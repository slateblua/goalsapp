package com.bluesourceplus.bluedays.data

import androidx.compose.runtime.Immutable

@Immutable
data class GoalModel(
    val id: Int = 0,
    val title: String,
    val description: String,
)