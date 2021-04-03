package com.costular.decorit.presentation.photos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.presentation.components.PhotoGrid
import kotlinx.coroutines.flow.collect

@Composable
fun PhotosScreen(onPhotoClick: (photo: Photo) -> Unit) {
    val viewModel: PhotosViewModel = hiltNavGraphViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is PhotosEvents.OpenPhoto -> onPhotoClick.invoke(event.photo)
            }
        }
    }

    PhotoContent(
        state = state,
        onPhotoClick = onPhotoClick,
        loadNextPage = { viewModel.load() }
    )
}

@Composable
fun PhotoContent(
    state: PhotosState,
    onPhotoClick: (photo: Photo) -> Unit,
    loadNextPage: () -> Unit
) {
    if (state.loadingMore && state.photos.isEmpty()) {
        LoadingOverlay()
    } else {
        PhotoGrid(
            photos = state.photos,
            modifier = Modifier.fillMaxSize(),
            isLoadingMore = state.loadingMore,
            onPhotoClick = { onPhotoClick(it) },
            loadNextPage = { loadNextPage() }
        )
    }
}

@Composable
private fun LoadingOverlay() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}