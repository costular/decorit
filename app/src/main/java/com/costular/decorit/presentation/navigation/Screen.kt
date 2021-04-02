package com.costular.decorit.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.costular.decorit.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Photos : Screen("photos", R.string.nav_photos, Icons.Outlined.Wallpaper)
    object Search : Screen("search", R.string.nav_search, Icons.Outlined.Search)
    object Favorites : Screen("favorites", R.string.nav_favorites, Icons.Outlined.FavoriteBorder)
    object Settings : Screen("settings", R.string.nav_settings, Icons.Outlined.Settings)
}