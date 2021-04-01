package com.costular.decorit.presentation.photos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.presentation.components.StaggeredVerticalGrid
import com.costular.decorit.presentation.components.Wallpaper

@Composable
fun PhotosScreen() {
    val viewModel: PhotosViewModel = hiltNavGraphViewModel()
    val state by viewModel.state.collectAsState()

    PhotoGrid(
        photos = state.photos,
        isLoadingMore = state.loadingMore,
        onPhotoClick = { viewModel.openPhoto(it) }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PhotoGrid(
    photos: List<Photo>,
    modifier: Modifier = Modifier,
    isLoadingMore: Boolean, // TODO: 1/4/21 use loading
    onPhotoClick: (photo: Photo) -> Unit
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
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
}