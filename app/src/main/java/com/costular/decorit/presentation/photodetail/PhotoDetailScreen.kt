package com.costular.decorit.presentation.photodetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import coil.transform.CircleCropTransformation
import com.costular.decorit.R
import com.costular.decorit.domain.Async
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer
import com.costular.decorit.presentation.photos.PhotosEvents
import com.costular.decorit.presentation.search.SearchViewModel
import com.costular.decorit.presentation.ui.DecoritTheme
import com.google.accompanist.coil.CoilImage
import kotlinx.coroutines.flow.collect
import android.content.Intent
import androidx.core.net.toUri
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun PhotoDetailScreen(photoId: String, onGoBack: () -> Unit) {
    val viewModel: PhotoDetailViewModel = hiltNavGraphViewModel()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is PhotoDetailEvents.SetAsWallpaper -> {
                    val intent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                        addCategory(Intent.CATEGORY_DEFAULT)
                        setDataAndType(event.uri.toUri(), "image/jpeg")
                        putExtra("mimeType", "image/jpeg")
                    }
                    context.startActivity(
                        Intent.createChooser(
                            intent,
                            "Set as:"
                        )
                    ) // TODO: 6/4/21 do not harcode "set as" text
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.load(photoId)
    }

    val state by viewModel.state.collectAsState()

    when (val photoState = state.photo) {
        is Async.Loading -> {
            Loading()
        }
        is Async.Success -> {
            Success(
                photoState(),
                onDownload = {
                    viewModel.download()
                },
                onSetAsWallpaper = {
                    viewModel.setAsWallpaper()
                },
                isDownloading = state.isDownloading
            )
        }
        is Async.Failure -> {
            Failure()
        }
    }

    CloseIcon(onGoBack)
}

@Composable
private fun CloseIcon(onGoBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.TopStart),
            onClick = {
                onGoBack()
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp, 24.dp),
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
private fun ActionsBar(
    photographer: Photographer,
    source: String,
    isDownloading: Boolean,
    onDownload: () -> Unit,
    onSetAsWallpaper: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
        PhotographerAvatar(avatarUrl = photographer.avatar, modifier = Modifier.size(40.dp))
        PhotographerNameAndSource(
            name = photographer.name,
            source = source,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .weight(1f)
        )
        ActionButtons(
            modifier = Modifier.weight(1f),
            onDownload = onDownload,
            onSetAsWallpaper = onSetAsWallpaper,
            isDownloading = isDownloading
        )
    }
}

@Composable
private fun PhotographerAvatar(
    avatarUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)) {
        if (avatarUrl != null) {
            Image(
                painter = rememberCoilPainter(
                    request = avatarUrl,
                    requestBuilder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = "avatar",
                modifier = modifier,
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.onBackground)
            )
        }
    }
}

@Composable
private fun PhotographerNameAndSource(
    name: String,
    source: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(text = name)
        Text(text = source)
    }
}

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    isDownloading: Boolean,
    onDownload: () -> Unit,
    onSetAsWallpaper: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = modifier.padding(end = 16.dp, bottom = 16.dp)
    ) {
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.onBackground,
            contentColor = MaterialTheme.colors.background,
            modifier = Modifier.size(40.dp),
            onClick = onDownload,
        ) {
            if (isDownloading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Icon(imageVector = Icons.Default.FileDownload, contentDescription = "download")
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        FloatingActionButton(
            backgroundColor = MaterialTheme.colors.onBackground,
            contentColor = MaterialTheme.colors.background,
            modifier = Modifier.size(40.dp),
            onClick = onSetAsWallpaper,
        ) {
            Icon(imageVector = Icons.Default.Wallpaper, contentDescription = "set as wallpaper")
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun Success(
    photo: Photo,
    isDownloading: Boolean,
    onDownload: () -> Unit,
    onSetAsWallpaper: () -> Unit
) {
    Box {
        Image(
            painter = rememberCoilPainter(request = photo.large, fadeIn = true),
            modifier = Modifier.fillMaxSize(),
            contentDescription = "image"
        )

        ActionsBar(
            onDownload = onDownload,
            onSetAsWallpaper = onSetAsWallpaper,
            photographer = photo.photographer,
            source = photo.sourceId,
            isDownloading = isDownloading
        )
    }

}

@Composable
private fun Failure() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Something wen't wrong :(", // TODO: 6/4/21 do not hardcode this
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )
    }
}