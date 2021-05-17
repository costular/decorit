package com.costular.decorit.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.coil.CoilImage
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun Wallpaper(
    photoUrl: String,
    modifier: Modifier = Modifier,
    onPhotoClicked: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable { onPhotoClicked?.invoke() }
    ) {
        Image(
            painter = rememberCoilPainter(request = photoUrl, fadeIn = true),
            contentDescription = "Wallpaper",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(name = "Preview light photo", widthDp = 300, heightDp = 400)
@Composable
private fun PreviewWallpaper() {
    Wallpaper(photoUrl = "https://wallpapercave.com/wp/wp6124195.jpg")
}