package com.bluesourceplus.bluedays.feature.aboutgoalscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases.GetGoalByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed interface State {
    data class Content(
        val id: Int = 0,
        val title: String = "",
        val description: String = "",
        val dueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
        val completed: Boolean = false,
    ) : State
}


sealed interface Event {
    data class LoadGoal(
        val goalId: Int,
    ) : Event
}


class AboutGoalViewModel : ViewModel(), KoinComponent {
    private val getGoalByIdUseCase: GetGoalByIdUseCase by inject()

    private val _state =
        MutableStateFlow<State>(
            State.Content()
        )
    val state = _state.asStateFlow()

    fun handleEvent(event: Event) {
        when (event) {
            is Event.LoadGoal -> loadGoal(event.goalId)
        }
    }

    private fun loadGoal(goalId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val goal = getGoalByIdUseCase(goalId).first()
            _state.update {
                State.Content(
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