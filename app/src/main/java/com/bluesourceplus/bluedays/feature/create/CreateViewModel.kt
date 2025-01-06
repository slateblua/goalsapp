package com.bluesourceplus.bluedays.feature.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.feature.aboutgoalscreen.usecases.GetGoalByIdUseCase
import com.bluesourceplus.bluedays.feature.create.usecases.AddGoalUseCase
import com.bluesourceplus.bluedays.feature.create.usecases.UpdateGoalUseCase
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

sealed class CreateGoalMode {
    data object Create : CreateGoalMode()
    data class Edit(val goalId: Int) : CreateGoalMode()
}

sealed interface Event {
    data class OnTitleChanged(
        val title: String,
    ) : Event

    data class OnDescriptionChanged(
        val description: String,
    ) : Event

    data class OnDueDateChanged(
        val dueDate: LocalDate,
    ) : Event

    data object OnSaveClicked : Event
}

sealed interface State {
    data class Content(
        val id: Int = 0,
        val title: String = "",
        val description: String = "",
        val dueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
        val completed: Boolean = false,
    ) : State
}

sealed class Effect {
    data object TaskSaved : Effect()
    data object NavigateUp : Effect()
}

class CreateViewModel(private val mode: CreateGoalMode) : ViewModel(), KoinComponent {
    private val addGoalUseCase: AddGoalUseCase by inject()
    private val getGoalByIdUseCase: GetGoalByIdUseCase by inject()
    private val updateGoalUseCase: UpdateGoalUseCase by inject()

    init {
        if (mode is CreateGoalMode.Edit) {
            loadGoal(mode.goalId)
        }
    }

    private val _sideEffect = Channel<Effect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state =
        MutableStateFlow<State>(
            State.Content(),
        )
    val state = _state.asStateFlow()

    fun handleEvent(event: Event) {
        when (event) {
            is Event.OnTitleChanged -> setTitle(event.title)
            is Event.OnDescriptionChanged -> setDescription(event.description)
            is Event.OnSaveClicked -> addOrUpdateGoal()
            is Event.OnDueDateChanged -> setDueDate(event.dueDate)
        }
    }

    private fun setDueDate(dueDate: LocalDate) {
        _state.update {
            (it as State.Content).copy(dueDate = dueDate)
        }
    }

    private fun setTitle(title: String) {
        _state.update {
            (it as State.Content).copy(title = title)
        }
    }

    private fun setDescription(description: String) {
        _state.update {
            (it as State.Content).copy(description = description)
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
                    completed = goal.completed,
                )
            }
        }
    }

    private fun addOrUpdateGoal() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value as State.Content
            val goal =
                GoalModel(
                    id = state.id,
                    title = state.title,
                    description = state.description,
                    dueDate = state.dueDate,
                    completed = false,
                )

            if (mode is CreateGoalMode.Edit) {
                updateGoalUseCase(goal)
            } else {
                addGoalUseCase(goal)
            }

            _sideEffect.send(Effect.TaskSaved)
            _sideEffect.send(Effect.NavigateUp)
        }
    }
}
