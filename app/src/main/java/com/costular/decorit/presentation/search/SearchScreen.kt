package com.costular.decorit.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.costular.decorit.R
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.presentation.components.PhotoGrid
import com.costular.decorit.presentation.photos.PhotosEvents
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(onPhotoClick: (photo: Photo) -> Unit) {
    val viewModel: SearchViewModel = hiltNavGraphViewModel()
    val state by viewModel.state.collectAsState()

    val searchBarHeight = remember { mutableStateOf(0) }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is SearchEvents.OpenPhoto -> onPhotoClick.invoke(event.photo)
            }
        }
    }

    BottomSheetScaffold(
        sheetContent = { FilterDialog() },
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 0.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            PhotoGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = with(LocalDensity.current) { 8.dp + searchBarHeight.value.toDp() },
                    bottom = 16.dp
                ),
                photos = state.items,
                isLoadingMore = state.isLoading,
                photoQuality = state.photoQuality,
                onPhotoClick = { viewModel.openPhoto(it) },
                loadNextPage = { viewModel.search(loadNext = true) }
            )

            var searchQuery by rememberSaveable { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .onSizeChanged { searchBarHeight.value = it.height }
                    .background(MaterialTheme.colors.surface.copy(alpha = 0.85f))
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    SearchTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = searchQuery,
                        hint = stringResource(id = R.string.search_hint),
                        onValueChange = { newQuery ->
                            searchQuery = newQuery
                            viewModel.enqueueQuery(newQuery)
                        }
                    )
                }
            }

            /*
            val filterText = stringResource(id = R.string.search_filter_button)
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 16.dp, end = 16.dp),
                text = { Text(filterText) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = filterText
                    )
                },
                onClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }
            )
            TODO add filters
             */
        }
    }
}

@Composable
private fun FilterDialog(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Filter", style = MaterialTheme.typography.h6)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            AnimatedVisibility(
                visible = value.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = { onValueChange("") },
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = ""
                    )
                }
            }
        },
        placeholder = { Text(text = hint) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
    )
}

@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
private fun FilterDialogPreview() {
    FilterDialog()
}