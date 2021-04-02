package com.costular.decorit.presentation.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import com.costular.decorit.R
import com.costular.decorit.presentation.components.PhotoGrid
import com.costular.decorit.presentation.ui.DecoritTheme
import com.costular.decorit.presentation.ui.placeholder
import timber.log.Timber

@Composable
fun SearchScreen() {
    val viewModel: SearchViewModel = hiltNavGraphViewModel()
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        PhotoGrid(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = 64.dp,
                bottom = 16.dp
            ),
            photos = state.items,
            isLoadingMore = state.isLoading,
            onPhotoClick = { viewModel.openPhoto(it) },
            loadNextPage = { viewModel.search() }
        )

        SearchText(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .align(Alignment.TopCenter)
                .padding(16.dp),
            onTextChanged = { newQuery ->
                viewModel.enqueueQuery(newQuery)
            }
        )

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

@Composable
private fun SearchText(
    modifier: Modifier = Modifier,
    defaultValue: String = "",
    onTextChanged: (newQuery: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf(defaultValue) }
    val disabled = MaterialTheme.colors.onBackground.copy(alpha = 0.38f)

    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, disabled)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.padding(start = 16.dp))
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = disabled)
            Spacer(modifier = Modifier.padding(start = 16.dp))
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                value = text,
                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                cursorBrush = SolidColor(MaterialTheme.colors.onSurface),
                onValueChange = { newValue ->
                    text = newValue
                    onTextChanged.invoke(newValue)
                },
                singleLine = true
            )
        }
    }
}

@Preview(widthDp = 300, heightDp = 70)
@Composable
private fun SearchPreview() {
    DecoritTheme {
        SearchText(
            modifier = Modifier.fillMaxWidth(),
            onTextChanged = {},
            defaultValue = "Tennis"
        )
    }
}