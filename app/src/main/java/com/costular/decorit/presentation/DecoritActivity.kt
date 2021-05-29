package com.costular.decorit.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.costular.decorit.presentation.more.MoreScreen
import com.costular.decorit.presentation.navigation.Screen
import com.costular.decorit.presentation.photodetail.PhotoDetailScreen
import com.costular.decorit.presentation.photos.PhotosScreen
import com.costular.decorit.presentation.search.SearchScreen
import com.costular.decorit.presentation.ui.DecoritTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DecoritActivity : AppCompatActivity() {

    private val routes = listOf(
        Screen.Photos,
        Screen.Search,
        Screen.More
    )
    private val routeDestinations = routes.map { it.route }

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
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val isHomeDestination by remember(navBackStackEntry) {
            derivedStateOf {
                navBackStackEntry?.destination?.route in routeDestinations
            }
        }

        Scaffold(
            bottomBar = {
                if (isHomeDestination) {
                    BottomNavigation(navController = navController, routes = routes)
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(navController, startDestination = Screen.Photos.route) {
                    composable(Screen.Photos.route) {
                        PhotosScreen(
                            onPhotoClick = { photo ->
                                navController.navigate("photos/${photo.id}")
                            }
                        )
                    }
                    composable(Screen.Search.route) {
                        SearchScreen(onPhotoClick = { photo ->
                            navController.navigate("photos/${photo.id}")
                        })
                    }
                    composable(Screen.More.route) {
                        MoreScreen()
                    }
                    composable("photos/{photoId}") { backStackEntry ->
                        PhotoDetailScreen(
                            photoId = backStackEntry.arguments?.getString("photoId")!!,
                            onGoBack = {
                                navController.popBackStack()
                            }
                        )
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
            val currentRoute = navBackStackEntry?.destination?.route
            routes.forEach { screen ->
                BottomNavigationItem(
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onSurface,
                    icon = { Icon(imageVector = screen.icon, "icon") },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.startDestinationRoute!!) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

}