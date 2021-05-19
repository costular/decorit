package com.costular.decorit.presentation.more

import androidx.lifecycle.viewModelScope
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider
) : MviViewModel<MoreState>(MoreState()) {
    private val menuItems: List<MenuItem> =
        listOf(
            MenuItem.GitHub,
            MenuItem.Settings
        )

    init {
        viewModelScope.launchSetState {
            copy(items = menuItems)
        }
    }

    fun openGitHub() {
        sendEvent(MoreEvents.OpenGitHub("https://github.com/costular/decorit"))
    }

    fun openSettings() {
        sendEvent(MoreEvents.OpenSettings)
    }

}