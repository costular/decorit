package com.costular.decorit.presentation.more


data class MoreState(
    val items: List<MenuItem> = emptyList()
)

sealed class MenuItem {

    object GitHub : MenuItem()

    object Settings : MenuItem()

}