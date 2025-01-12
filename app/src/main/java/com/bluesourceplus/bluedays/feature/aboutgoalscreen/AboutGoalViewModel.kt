package com.bluesourceplus.bluedays.feature.aboutgoalscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases.DeleteGoalUseCase
import com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases.GetGoalByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed interface AboutGoalState {
    data class Content(
        val id: Int = 0,
        val title: String = "",
        val description: String = "",
        val dueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
        val completed: Boolean = false,
    ) : AboutGoalState
}

sealed class AboutGoalEffect {
    data object GoalDeleted : AboutGoalEffect()
}

sealed interface AboutGoalIntent {
    data class LoadGoal(
        val goalId: Int,
    ) : AboutGoalIntent

    data class DeleteGoal(
        val id: Int,
    ) : AboutGoalIntent
}


class AboutGoalViewModel : ViewModel(), KoinComponent {
    private val getGoalByIdUseCase: GetGoalByIdUseCase by inject()
    private val deleteGoalUseCase: DeleteGoalUseCase by inject()

    private val _state =
        MutableStateFlow<AboutGoalState>(
            AboutGoalState.Content()
        )
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<AboutGoalEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun handleEvent(event: AboutGoalIntent) {
        when (event) {
            is AboutGoalIntent.LoadGoal -> loadGoal(event.goalId)
            is AboutGoalIntent.DeleteGoal -> deleteGoal(goalId = event.id)
        }
    }

    private fun deleteGoal(goalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val goal = getGoalByIdUseCase(goalId)
            deleteGoalUseCase(goal.first())
            _sideEffect.send(AboutGoalEffect.GoalDeleted)
        }
    }

    private fun loadGoal(goalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val goal = getGoalByIdUseCase(goalId).first()
            _state.update {
                AboutGoalState.Content(
                    id = goalId,
                    title = goal.title,
                    description = goal.description,
                    dueDate = goal.dueDate,
                    completed = goal.completed
                )
            }
        }
    }
}