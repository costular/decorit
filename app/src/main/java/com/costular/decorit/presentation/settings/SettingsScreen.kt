package com.costular.decorit.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.decorit.R
import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.model.Theme
import com.costular.decorit.util.rememberFlowWithLifecycle
import androidx.compose.runtime.getValue

@Composable
fun SettingsScreen() {
    val viewModel: SettingsViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = SettingsState())
    var showChooseThemeDialog by rememberSaveable { mutableStateOf(false) }
    var showViewQualityDialog by rememberSaveable { mutableStateOf(false) }
    var showDownloadQualityDialog by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        SettingsContent(
            state,
            onChooseTheme = { showChooseThemeDialog = true },
            onChooseViewQuality = { showViewQualityDialog = true },
            onChooseDownloadQuality = { showDownloadQualityDialog = true }
        )

        if (showChooseThemeDialog) {
            val selectedTheme = state.selectedTheme
            if (selectedTheme != null) {
                ThemeSettingDialog(
                    onDismissRequest = {
                        showChooseThemeDialog = false
                    },
                    onThemeSelected = { theme ->
                        viewModel.selectTheme(theme)
                        showChooseThemeDialog = false
                    },
                    availableThemes = state.availableThemes,
                    selectedTheme = selectedTheme
                )
            }
        }

        if (showViewQualityDialog) {
            val viewQuality = state.viewQuality
            QualitySettingDialog(
                title = stringResource(R.string.preferences_quality_view),
                onDismissRequest = { showViewQualityDialog = false },
                onQualitySelected = { quality ->
                    showViewQualityDialog = false
                    viewModel.selectViewQuality(quality)
                },
                availableQualities = state.availableQualities,
                selectedQuality = viewQuality
            )
        }

        if (showDownloadQualityDialog) {
            val downloadQuality = state.downloadQuality
            QualitySettingDialog(
                title = stringResource(R.string.preferences_quality_download),
                onDismissRequest = { showDownloadQualityDialog = false },
                onQualitySelected = { quality ->
                    showDownloadQualityDialog = false
                    viewModel.selectDownloadQuality(quality)
                },
                availableQualities = state.availableQualities,
                selectedQuality = downloadQuality
            )
        }
    }
}

@Composable
private fun SettingsContent(
    state: SettingsState,
    onChooseTheme: () -> Unit,
    onChooseViewQuality: () -> Unit,
    onChooseDownloadQuality: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SettingsBar(modifier = Modifier.fillMaxWidth())
        },
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SettingHeader(text = stringResource(id = R.string.preferences_appearance_title))
            if (state.selectedTheme != null) {
                ChooseThemeSetting(
                    selectedTheme = state.selectedTheme,
                    onClick = { onChooseTheme() })
            }

            SettingHeader(text = stringResource(id = R.string.preferences_general_title))
            if (state.viewQuality != null) {
                ChooseQuality(
                    title = stringResource(R.string.preferences_quality_view),
                    selectedQuality = state.viewQuality,
                    onClick = { onChooseViewQuality() }
                )
            }

            if (state.downloadQuality != null) {
                ChooseQuality(
                    title = stringResource(R.string.preferences_quality_download),
                    selectedQuality = state.downloadQuality,
                    onClick = { onChooseDownloadQuality() }
                )
            }
        }
    }
}

@Composable
private fun SettingsBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.more_menu_settings))
        },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onBackground,
        modifier = modifier,
        elevation = 0.dp
    )
}

@Composable
private fun SettingHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.caption,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
private fun ChooseThemeSetting(
    selectedTheme: Theme,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.preferences_theme_title),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = getTitleForTheme(selectedTheme),
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun ChooseQuality(
    title: String,
    selectedQuality: PhotoQuality,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = getTitleForQuality(selectedQuality),
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ChooseThemePreview() {
    ChooseThemeSetting(Theme.SYSTEM, onClick = {})
}