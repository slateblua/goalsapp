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

sealed interface CreateGoalIntent {
    data class OnTitleChanged(
        val title: String,
    ) : CreateGoalIntent

    data class OnDescriptionChanged(
        val description: String,
    ) : CreateGoalIntent

    data class OnDueDateChanged(
        val dueDate: LocalDate,
    ) : CreateGoalIntent

    data object OnSaveClicked : CreateGoalIntent
}

sealed interface State {
    data class Content(
        val goalId: Int = 0,
        val title: String = "",
        val description: String = "",
        val dueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
        val completed: Boolean = false,
    ) : State
}

sealed class CreateGoalEffect {
    data object GoalSaved : CreateGoalEffect()
    data object NavigateUp : CreateGoalEffect()
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

    private val _sideEffect = Channel<CreateGoalEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state =
        MutableStateFlow<State>(
            State.Content(),
        )
    val state = _state.asStateFlow()

    fun handleEvent(event: CreateGoalIntent) {
        when (event) {
            is CreateGoalIntent.OnTitleChanged -> setTitle(event.title)
            is CreateGoalIntent.OnDescriptionChanged -> setDescription(event.description)
            is CreateGoalIntent.OnSaveClicked -> addOrUpdateGoal()
            is CreateGoalIntent.OnDueDateChanged -> setDueDate(event.dueDate)
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
                    goalId = goalId,
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
                    goalId = state.goalId,
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

            _sideEffect.send(CreateGoalEffect.GoalSaved)
            _sideEffect.send(CreateGoalEffect.NavigateUp)
        }
    }
}
