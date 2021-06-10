package com.costular.decorit.presentation.photodetail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import com.costular.decorit.R
import com.costular.decorit.domain.Async
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer
import kotlinx.coroutines.flow.collect
import android.content.Intent
import android.text.style.UnderlineSpan
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.coil.rememberCoilPainter
import kotlinx.coroutines.launch

@Composable
fun PhotoDetailScreen(photoId: String, onGoBack: () -> Unit) {
    val viewModel: PhotoDetailViewModel = hiltViewModel()
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

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
                            context.getString(R.string.wallpaper_set_as)
                        )
                    )
                }
                is PhotoDetailEvents.DownloadedSuccessfully -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(context.getString(R.string.photo_downloaded_successfully))
                    }
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
            Scaffold(scaffoldState = scaffoldState) {
                Success(
                    photoState(),
                    onDownload = {
                        viewModel.download()
                    },
                    onSetAsWallpaper = {
                        viewModel.setAsWallpaper()
                    },
                    isDownloading = state.isDownloading,
                    isSettingAsWallpaper = state.isSettingAsWallpaper
                )
            }
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
    sourceLink: String,
    isDownloading: Boolean,
    isSettingAsWallpaper: Boolean,
    onDownload: () -> Unit,
    onSetAsWallpaper: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
        PhotographerAvatar(avatarUrl = photographer.avatar, modifier = Modifier.size(40.dp))
        PhotographerNameAndSource(
            name = photographer.name,
            authorLink = photographer.link,
            source = source,
            link = sourceLink,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp)
                .weight(1f)
        )
        ActionButtons(
            modifier = Modifier.weight(1f),
            onDownload = onDownload,
            onSetAsWallpaper = onSetAsWallpaper,
            isDownloading = isDownloading,
            isSettingAsWallpaper = isSettingAsWallpaper
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
    authorLink: String,
    source: String,
    link: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(modifier) {
        val authorAnnotated = buildAnnotatedString {
            pushStringAnnotation(
                tag = "URL",
                annotation = authorLink
            )
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(name)
            }
            pop()
        }
        ClickableText(
            text = authorAnnotated,
            onClick = { offset ->
                handleClick(context, authorAnnotated, offset)
            }
        )

        val providedBy = stringResource(R.string.provided_by)
        val annotatedString = buildAnnotatedString {
            append("$providedBy ")
            pushStringAnnotation(
                tag = "URL",
                annotation = link
            )
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append(source)
            }
            pop()
        }
        ClickableText(
            text = annotatedString,
            onClick = { offset ->
                handleClick(context, annotatedString, offset)
            }
        )
    }
}

private fun handleClick(context: Context, annotatedString: AnnotatedString, offset: Int) {
    annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
        .firstOrNull()
        ?.let { annotation ->
            val intent = Intent(Intent.ACTION_VIEW, annotation.item.toUri())
            context.startActivity(intent)
        }
}

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    isDownloading: Boolean,
    isSettingAsWallpaper: Boolean,
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
            if (isSettingAsWallpaper) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Icon(imageVector = Icons.Default.Wallpaper, contentDescription = "set as wallpaper")
            }
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
    isSettingAsWallpaper: Boolean,
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
            source = photo.source.name,
            sourceLink = photo.source.link,
            isDownloading = isDownloading,
            isSettingAsWallpaper = isSettingAsWallpaper
        )
    }

}

@Composable
private fun Failure() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(id = R.string.something_went_wrong),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5
        )
    }
}