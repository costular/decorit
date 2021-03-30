package com.costular.decorit.presentation.base

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class MviViewModel<S : MavericksState>(state: S) : MavericksViewModel<S>(state) {

    private val _uiEvents = MutableSharedFlow<UiEvent>(extraBufferCapacity = 100)

    val uiEvents: Flow<UiEvent>
        get() = _uiEvents.asSharedFlow()

    protected fun sendEvent(uiEvent: UiEvent) = viewModelScope.launch {
        _uiEvents.emit(uiEvent)
    }

}