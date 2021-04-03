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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.costular.decorit.R
import com.costular.decorit.presentation.components.PhotoGrid

@Composable
fun SearchScreen() {
    val viewModel: SearchViewModel = hiltNavGraphViewModel()
    val state by viewModel.state.collectAsState()

    val searchBarHeight = remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        PhotoGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = with(LocalDensity.current) { searchBarHeight.value.toDp() },
                bottom = 16.dp
            ),
            photos = state.items,
            isLoadingMore = state.isLoading,
            onPhotoClick = { viewModel.openPhoto(it) },
            loadNextPage = { viewModel.search() }
        )

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
                    value = TextFieldValue(""),
                    hint = stringResource(id = R.string.search_hint),
                    onValueChange = { newQuery ->
                        viewModel.enqueueQuery(newQuery.text)
                    }
                )
            }
        }

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
                // TODO: 3/4/21
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
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
                visible = value.text.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(
                    onClick = { onValueChange(TextFieldValue()) },
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