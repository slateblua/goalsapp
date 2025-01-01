package com.bluesourceplus.bluedays.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.feature.home.usecases.GetAllGoalsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed interface State {
    data object Empty : State

    data class Content(
        val goals: List<GoalModel>,
    ) : State
}

class HomeViewModel : ViewModel(), KoinComponent {
    private val getAllNotesUseCase: GetAllGoalsUseCase by inject()

    private val goals = getAllNotesUseCase()

    val state: StateFlow<State> =
        goals
            .map { goals ->
                if (goals.isNotEmpty()) {
                    State.Content(goals)
                } else {
                    State.Empty
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = State.Empty,
            )
}
