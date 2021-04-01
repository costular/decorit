package com.costular.decorit.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
import androidx.compose.material.Icon
import androidx.compose.ui.res.painterResource
import com.costular.decorit.presentation.navigation.Screen
import com.costular.decorit.presentation.photos.PhotosScreen
import com.costular.decorit.presentation.ui.DecoritTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DecoritActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val routes = listOf(
                Screen.Photos
            )
            val navController = rememberNavController()

            DecoritTheme {
                Scaffold {
                    BottomNavigation {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                        routes.forEach { screen ->
                            BottomNavigationItem(
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

                NavHost(navController, startDestination = Screen.Photos.route) {
                    composable(Screen.Photos.route) {
                        PhotosScreen(navController)
                    }
                }
            }
        }
    }

}