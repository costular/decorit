package com.costular.decorit.presentation.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.costular.decorit.presentation.components.PhotoGrid

@Composable
fun PhotosScreen() {
    val viewModel: PhotosViewModel = hiltNavGraphViewModel()
    val state by viewModel.state.collectAsState()

    if (state.loadingMore && state.photos.isEmpty()) {
        LoadingOverlay()
    } else {
        PhotoGrid(
            photos = state.photos,
            isLoadingMore = state.loadingMore,
            onPhotoClick = { viewModel.openPhoto(it) },
            loadNextPage = { viewModel.load() }
        )
    }
}

@Composable
private fun LoadingOverlay() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}