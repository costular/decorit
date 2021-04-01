package com.costular.decorit.presentation

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Device
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.costular.decorit.presentation.navigation.Screen
import com.costular.decorit.presentation.photos.PhotosScreen
import com.costular.decorit.presentation.search.SearchScreen
import com.costular.decorit.presentation.ui.DecoritTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DecoritActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DecoritTheme {
                Navigation()
            }
        }
    }

    @Composable
    private fun Navigation() {
        val routes = listOf(
            Screen.Photos,
            Screen.Search,
            Screen.Favorites,
            Screen.Settings
        )
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigation(navController = navController, routes = routes)
            }
        ) { innerPading ->
            Box(modifier = Modifier.padding(innerPading)) {
                NavHost(navController, startDestination = Screen.Photos.route) {
                    composable(Screen.Photos.route) {
                        PhotosScreen()
                    }
                    composable(Screen.Search.route) {
                        SearchScreen()
                    }
                    composable(Screen.Favorites.route) {
                        SearchScreen()
                    }
                    composable(Screen.Settings.route) {
                        SearchScreen()
                    }
                }
            }
        }
    }

    @Composable
    private fun BottomNavigation(navController: NavController, routes: List<Screen>) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.onSurface
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
            routes.forEach { screen ->
                BottomNavigationItem(
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onSurface,
                    alwaysShowLabel = false,
                    icon = { Icon(imageVector = screen.icon, "icon") },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo = navController.graph.startDestination
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }

}