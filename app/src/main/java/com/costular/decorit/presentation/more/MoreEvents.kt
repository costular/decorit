package com.costular.decorit.presentation.more

import com.costular.decorit.presentation.base.UiEvent

sealed class MoreEvents : UiEvent {

    data class OpenGitHub(val url: String) : MoreEvents()

    object OpenSettings : MoreEvents()

}