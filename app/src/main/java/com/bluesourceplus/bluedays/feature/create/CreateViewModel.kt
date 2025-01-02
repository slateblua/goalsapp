package com.bluesourceplus.bluedays.feature.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bluesourceplus.bluedays.data.GoalModel
import com.bluesourceplus.bluedays.feature.create.usecases.AddGoalUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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
        val dueDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
    ) : State
}

class CreateViewModel : ViewModel(), KoinComponent {
    private val addGoalUseCase: AddGoalUseCase by inject()

    private val backNavigationEventChannel = Channel<Unit>(Channel.BUFFERED)
    val backNavigationEvent: Flow<Unit> = backNavigationEventChannel.receiveAsFlow()

    private val _state =
        MutableStateFlow<State>(
            State.Content(),
        )
    val state = _state.asStateFlow()

    fun handleEvent(event: Event) {
        when (event) {
            is Event.OnTitleChanged -> setTitle(event.title)
            is Event.OnDescriptionChanged -> setDescription(event.description)
            is Event.OnSaveClicked -> addOrUpdateNote()
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

    private fun addOrUpdateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value as State.Content
            val goal =
                GoalModel(
                    id = state.id,
                    title = state.title,
                    description = state.description,
                    dueDate = state.dueDate
                )
            addGoalUseCase(goal)
            backNavigationEventChannel.send(Unit)
        }
    }
}
