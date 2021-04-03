package com.costular.decorit.presentation.photodetail

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.costular.decorit.domain.Async
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer
import com.costular.decorit.presentation.search.SearchViewModel
import com.costular.decorit.presentation.ui.DecoritTheme
import com.google.accompanist.coil.CoilImage

@Composable
fun PhotoDetailScreen(photoId: String, onGoBack: () -> Unit) {
    val viewModel: PhotoDetailViewModel = hiltNavGraphViewModel()
    LaunchedEffect(Unit) {
        viewModel.load(photoId)
    }

    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 16.dp),
            onClick = {
                onGoBack()
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp, 24.dp),
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                tint = Color.White
            )
        }

        when (val photoState = state.photo) {
            is Async.Loading -> {
                Loading()
            }
            is Async.Success -> {
                Success(photoState())
            }
            is Async.Failure -> {
                Failure()
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
private fun Success(photo: Photo) {
    CoilImage(
        data = photo.large,
        modifier = Modifier.fillMaxSize(),
        fadeIn = true,
        contentDescription = "image"
    )
}

@Composable
private fun Failure() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Something wen't wrong :(",
            modifier = Modifier.fillMaxWidth()
        )
    }
}