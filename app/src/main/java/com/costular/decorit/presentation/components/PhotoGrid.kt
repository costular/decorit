package com.costular.decorit.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.PhotoQuality
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGrid(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 16.dp),
    listState: LazyListState = rememberLazyListState(),
    isLoadingMore: Boolean, // TODO: 1/4/21 use loading
    photoQuality: PhotoQuality = PhotoQuality.Medium,
    onPhotoClick: (photo: Photo) -> Unit,
    loadNextPage: (() -> Unit)? = null,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        state = listState
    ) {
        itemsIndexed(photos) { index, photo ->
            Wallpaper(
                photoUrl = photo.photoUrlFromQuality(photoQuality),
                modifier = Modifier.fillMaxHeight(0.7f),
                onPhotoClicked = { onPhotoClick.invoke(photo) }
            )

            if (index == photos.lastIndex) {
                LaunchedEffect(Unit) {
                    loadNextPage?.invoke()
                }
            }
        }
    }
}

@Composable
private fun LoadingMore(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}