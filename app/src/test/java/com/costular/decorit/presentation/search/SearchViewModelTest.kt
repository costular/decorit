package com.costular.decorit.presentation.search

import android.graphics.Color
import android.widget.SearchView
import com.costular.decorit.data.SourceConstants
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.*
import com.costular.decorit.presentation.ReduxViewModelTest
import com.costular.decorit.presentation.testBlocking
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.uniflow.android.test.TestViewObserver
import io.uniflow.android.test.createTestObserver
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class SearchViewModelTest : ReduxViewModelTest() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var view: TestViewObserver

    private val getPhotosInteractor: GetPhotosInteractor = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = SearchViewModel(dispatcherProvider, getPhotosInteractor)
        view = viewModel.createTestObserver()
    }

    @Test
    fun `Test get photos`() = testBlocking {
        // Given
        val photos = listOf(
            Photo(
                "1",
                1080,
                1080,
                SourceConstants.UNSPLASH,
                Photographer(
                    "1",
                    "john",
                    "avatar"
                ),
                false,
                "original",
                "full",
                "regular",
                "small"
            )
        )

        every { getPhotosInteractor(any()) } returns flowOf(photos)

        // When
        viewModel.search()

        // Then
        val lastState = view.lastStateOrNull as SearchState
        Truth.assertThat(lastState.items).isEqualTo(photos)
    }

    @Test
    fun `Test get photos next page`() = testBlocking {
        // Given
        val photos = listOf(
            Photo(
                "1",
                1080,
                1080,
                SourceConstants.UNSPLASH,
                Photographer(
                    "1",
                    "john",
                    "avatar"
                ),
                false,
                "original",
                "full",
                "regular",
                "small"
            )
        )
        val secondPagePhotos = listOf(
            Photo(
                "2",
                1080,
                1080,
                SourceConstants.UNSPLASH,
                Photographer(
                    "2",
                    "john2",
                    "avatar2"
                ),
                false,
                "original2",
                "full2",
                "regular2",
                "small2"
            )
        )

        every {
            getPhotosInteractor(
                GetPhotosInteractor.Params(
                    1,
                    20,
                    SearchParams()
                )
            )
        } returns flowOf(photos)

        every {
            getPhotosInteractor(
                GetPhotosInteractor.Params(
                    2,
                    20,
                    SearchParams()
                )
            )
        } returns flowOf(secondPagePhotos)

        // When
        viewModel.search()
        viewModel.search(loadNext = true)

        // Then
        val lastState = view.lastStateOrNull as SearchState
        Truth.assertThat(lastState.items).isEqualTo(photos + secondPagePhotos)
    }

    @Test
    fun `Test search and then clear`() = testBlocking {
        // Given
        val photos = listOf(
            Photo(
                "1",
                1080,
                1080,
                SourceConstants.UNSPLASH,
                Photographer(
                    "1",
                    "john",
                    "avatar"
                ),
                false,
                "original",
                "full",
                "regular",
                "small"
            )
        )
        val secondPagePhotos = listOf(
            Photo(
                "2",
                1080,
                1080,
                SourceConstants.UNSPLASH,
                Photographer(
                    "2",
                    "john2",
                    "avatar2"
                ),
                false,
                "original2",
                "full2",
                "regular2",
                "small2"
            )
        )

        every {
            getPhotosInteractor(
                GetPhotosInteractor.Params(
                    1,
                    20,
                    SearchParams(query = "whatever")
                )
            )
        } returns flowOf(photos)

        every {
            getPhotosInteractor(
                GetPhotosInteractor.Params(
                    1,
                    20,
                    SearchParams()
                )
            )
        } returns flowOf(secondPagePhotos)

        // When
        viewModel.search(query = "whatever")
        viewModel.search()

        // Then
        val lastState = view.lastStateOrNull as SearchState
        Truth.assertThat(lastState.items).isEqualTo(secondPagePhotos)
    }

    @Test
    fun `Test select orientation`() = testBlocking {
        // Given
        val orientation = PhotoOrientation.VERTICAL

        // When
        viewModel.selectOrientation(orientation)

        // Then
        val lastState = view.lastStateOrNull as SearchState
        Truth.assertThat(lastState.params.orientation).isEqualTo(orientation)
    }

    @Test
    fun `Test select color`() = testBlocking {
        // Given
        val color = PhotoColor(Color.WHITE, ColorValue.WHITE)

        // When
        viewModel.selectColor(color)

        // Then
        val lastState = view.lastStateOrNull as SearchState
        Truth.assertThat(lastState.params.color).isEqualTo(color)
    }

    @Test
    fun `Test select color updates color list`() = testBlocking {
        // Given
        val color = PhotoColor(Color.WHITE, ColorValue.WHITE)

        // When
        viewModel.selectColor(color)

        // Then
        val lastState = view.lastStateOrNull as SearchState
        Truth.assertThat(lastState.params.color).isEqualTo(color)
        Truth.assertThat(lastState.filterColors.find { it.color.value == color.value }?.isSelected)
            .isTrue()
    }

}