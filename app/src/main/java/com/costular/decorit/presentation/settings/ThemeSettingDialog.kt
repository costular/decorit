package com.costular.decorit.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.costular.decorit.R
import com.costular.decorit.domain.model.Theme
import com.costular.decorit.presentation.components.DecoritDialog

@Composable
fun ThemeSettingDialog(
    onDismissRequest: () -> Unit,
    onThemeSelected: (Theme) -> Unit,
    availableThemes: List<Theme>,
    selectedTheme: Theme,
    modifier: Modifier = Modifier
) {
    DecoritDialog(
        title = stringResource(R.string.preferences_theme_title),
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        ThemeDialogContent(
            onThemeSelected = onThemeSelected,
            availableThemes = availableThemes,
            selectedTheme = selectedTheme
        )
    }
}

@Composable
private fun ThemeDialogContent(
    onThemeSelected: (Theme) -> Unit,
    availableThemes: List<Theme>,
    selectedTheme: Theme
) {
    Column(Modifier.padding(top = 16.dp)) {
        Column(Modifier.selectableGroup()) {
            availableThemes.forEach { theme ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = theme == selectedTheme,
                            onClick = { onThemeSelected(theme) },
                            role = Role.RadioButton
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = getTitleForTheme(theme),
                        modifier = Modifier
                            .weight(1f)
                            .padding()
                    )
                    RadioButton(
                        selected = theme == selectedTheme,
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                }
            }
        }
    }
}

@Composable
internal fun getTitleForTheme(theme: Theme) = when (theme) {
    Theme.LIGHT -> stringResource(R.string.preferences_theme_light)
    Theme.DARK -> stringResource(R.string.preferences_theme_dark)
    Theme.SYSTEM -> stringResource(R.string.preferences_theme_system)
}