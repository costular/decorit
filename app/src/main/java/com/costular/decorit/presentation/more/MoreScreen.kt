package com.costular.decorit.presentation.more

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.decorit.R
import kotlinx.coroutines.flow.collect

@Composable
fun MoreScreen(
    onOpenSettings: () -> Unit
) {
    val viewModel: MoreViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is MoreEvents.OpenGitHub -> {
                    val webIntent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                    context.startActivity(webIntent)
                }
                is MoreEvents.OpenSettings -> {
                    onOpenSettings()
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AboutApp(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        MoreMenu(
            state = state,
            lazyListState = listState,
            onOpenGithub = { viewModel.openGitHub() },
            onOpenSettings = { viewModel.openSettings() }
        )
    }
}

@Composable
private fun AboutApp(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(
            shape = CircleShape,
            elevation = 8.dp,
            modifier = Modifier
                .width(80.dp)
                .height(80.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "icon",
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stringResource(id = R.string.more_app_about_description))
    }
}

@Composable
private fun MoreMenu(
    state: MoreState,
    lazyListState: LazyListState,
    onOpenGithub: () -> Unit,
    onOpenSettings: () -> Unit
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(state.items) { item ->
            when (item) {
                is MenuItem.GitHub -> {
                    MoreListItem(
                        text = stringResource(id = R.string.more_menu_github),
                        icon = Icons.Default.Code,
                        onClick = {
                            onOpenGithub()
                        }
                    )
                }
                is MenuItem.Settings -> {
                    MoreListItem(
                        text = stringResource(id = R.string.more_menu_settings),
                        icon = Icons.Outlined.Settings,
                        onClick = {
                            onOpenSettings()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MoreListItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                onClick()
            }
            .padding(vertical = 16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )

        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(start = 72.dp, end = 16.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MoreListItemComposable() {
    MoreListItem(text = "Settings", icon = Icons.Outlined.Settings, onClick = {})
}

@Preview(showBackground = true, device = Devices.PIXEL_3)
@Composable
fun MoreScreenPreview() {
    MoreScreen(onOpenSettings = {})
}