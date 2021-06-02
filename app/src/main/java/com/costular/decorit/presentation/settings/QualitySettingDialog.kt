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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.costular.decorit.R
import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.presentation.components.DecoritDialog

@Composable
fun QualitySettingDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onQualitySelected: (PhotoQuality) -> Unit,
    availableQualities: List<PhotoQuality>,
    selectedQuality: PhotoQuality,
    modifier: Modifier = Modifier
) {
    DecoritDialog(
        title = title,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        QualityDialogContent(
            onQualitySelected = onQualitySelected,
            availableQualities = availableQualities,
            selectedQuality = selectedQuality
        )
    }
}

@Composable
private fun QualityDialogContent(
    onQualitySelected: (PhotoQuality) -> Unit,
    availableQualities: List<PhotoQuality>,
    selectedQuality: PhotoQuality,
) {
    Column(Modifier.padding(top = 16.dp)) {
        Column(Modifier.selectableGroup()) {
            availableQualities.forEach { theme ->
                Row(
                    modifier = Modifier
                        .selectable(
                            selected = theme == selectedQuality,
                            onClick = { onQualitySelected(theme) },
                            role = Role.RadioButton
                        )
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = getTitleForQuality(theme),
                        modifier = Modifier
                            .weight(1f)
                            .padding()
                    )
                    RadioButton(
                        selected = theme == selectedQuality,
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                }
            }
        }
    }
}

@Composable
internal fun getTitleForQuality(quality: PhotoQuality) = when (quality) {
    PhotoQuality.Low -> stringResource(R.string.quality_low)
    PhotoQuality.Medium -> stringResource(R.string.quality_medium)
    PhotoQuality.High -> stringResource(R.string.quality_high)
    PhotoQuality.Full -> stringResource(R.string.quality_full)
}