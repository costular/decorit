package com.costular.decorit.presentation.base

import com.costular.decorit.core.net.DispatcherProvider
import io.uniflow.android.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState

abstract class ReduxViewModel(
    private val dispatcher: DispatcherProvider,
    initialState: UIState = UIState.Empty
) : AndroidDataFlow(initialState, defaultDispatcher = dispatcher.computation)