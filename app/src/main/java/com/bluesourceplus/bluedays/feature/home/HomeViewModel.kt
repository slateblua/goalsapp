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

sealed interface HomeScreenState {
    data object Empty : HomeScreenState

    data class Content(
        val goals: List<GoalModel>,
    ) : HomeScreenState
}

sealed interface HomeScreenIntent {
    data class OnMarkedCompleted(
        val goalModel: GoalModel,
    ) : HomeScreenIntent
}

class HomeViewModel : ViewModel(), KoinComponent {
    private val getAllGoalsUseCase: GetAllGoalsUseCase by inject()

    private val updateGoalUseCase: UpdateGoalUseCaseImpl by inject()

    private val goals = getAllGoalsUseCase()

    val state: StateFlow<HomeScreenState> =
        goals
            .map { goals ->
                if (goals.isNotEmpty()) {
                    HomeScreenState.Content(goals)
                } else {
                    HomeScreenState.Empty
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeScreenState.Empty,
            )

    fun handleEvent(event: HomeScreenIntent) {
        when (event) {
            is HomeScreenIntent.OnMarkedCompleted -> markCompleted(event.goalModel)
        }
    }

    private fun markCompleted(goalModel: GoalModel) {
        viewModelScope.launch(Dispatchers.IO) {
            updateGoalUseCase(goalModel.copy(completed = !goalModel.completed))
        }
    }
}
