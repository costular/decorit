package com.costular.decorit.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.costular.decorit.domain.model.Photo

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoGrid(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    isLoadingMore: Boolean, // TODO: 1/4/21 use loading
    onPhotoClick: (photo: Photo) -> Unit,
    loadNextPage: (() -> Unit)? = null
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(all = 8.dp),
    ) {
        itemsIndexed(photos) { index, photo ->
            if (index == photos.lastIndex) {
                loadNextPage?.invoke()
            }

            Wallpaper(
                photoUrl = photo.medium,
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .padding(8.dp),
                onPhotoClicked = { onPhotoClick.invoke(photo) }
            )
        }
    }
    /*
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        StaggeredVerticalGrid(
            maxColumnWidth = 220.dp,
            modifier = Modifier.padding(4.dp)
        ) {
            photos.forEach { photo ->
                Wallpaper(
                    photoUrl = photo.medium,
                    modifier = Modifier.padding(4.dp),
                    onPhotoClicked = { onPhotoClick.invoke(photo) }
                )
            }
        }
    }
     */
}