package com.bluesourceplus.bluedays.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.feature.home.usecases.GetAllGoalsUseCase
import com.bluesourceplus.bluedays.feature.home.usecases.UpdateGoalUseCaseImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed interface State {
    data object Empty : State

    data class Content(
        val goals: List<GoalModel>,
    ) : State
}

sealed interface Event {
    data class OnMarkedCompleted(
        val goalModel: GoalModel,
    ) : Event
}

class HomeViewModel : ViewModel(), KoinComponent {
    private val getAllNotesUseCase: GetAllGoalsUseCase by inject()

    private val updateGoalUseCase: UpdateGoalUseCaseImpl by inject()

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

    fun handleEvent(event: Event) {
        when (event) {
            is Event.OnMarkedCompleted -> markCompleted(event.goalModel)
        }
    }

    private fun markCompleted(goalModel: GoalModel) {
        viewModelScope.launch(Dispatchers.IO) {
            updateGoalUseCase(goalModel.copy(completed = !goalModel.completed))
        }
    }
}
