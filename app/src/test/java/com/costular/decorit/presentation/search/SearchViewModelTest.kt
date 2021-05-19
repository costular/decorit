package com.costular.decorit.presentation.search

import android.graphics.Color
import app.cash.turbine.test
import com.costular.decorit.core.TestDispatcherProvider
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.data.SourceConstants
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.interactor.GetViewPhotoQualityInteractor
import com.costular.decorit.domain.model.*
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testDispatcherProvider: DispatcherProvider =
        TestDispatcherProvider(testCoroutineDispatcher)
    private val getPhotosInteractor: GetPhotosInteractor = mockk(relaxed = true)
    private val getViewPhotoQualityInteractor: GetViewPhotoQualityInteractor = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = SearchViewModel(
            testDispatcherProvider,
            getPhotosInteractor,
            getViewPhotoQualityInteractor
        )
    }

    @Test
    fun `Test get photos`() = testCoroutineDispatcher.runBlockingTest {
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
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.items).isEqualTo(photos)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test get photos next page`() = testCoroutineDispatcher.runBlockingTest {
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
        viewModel.state.test {
            Truth.assertThat(expectItem().items).isEqualTo(photos + secondPagePhotos)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test search and then clear`() = testCoroutineDispatcher.runBlockingTest {
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
        viewModel.state.test {
            Truth.assertThat(expectItem().items).isEqualTo(secondPagePhotos)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test select orientation`() = testCoroutineDispatcher.runBlockingTest {
        // Given
        val orientation = PhotoOrientation.VERTICAL

        // When
        viewModel.selectOrientation(orientation)

        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.params.orientation).isEqualTo(orientation)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test select color`() = testCoroutineDispatcher.runBlockingTest {
        // Given
        val color = PhotoColor(Color.WHITE, ColorValue.WHITE)

        // When
        viewModel.selectColor(color)

        // Then
        viewModel.state.test {
            Truth.assertThat(expectItem().params.color).isEqualTo(color)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test select color updates color list`() = testCoroutineDispatcher.runBlockingTest {
        // Given
        val color = PhotoColor(Color.WHITE, ColorValue.WHITE)

        // When
        viewModel.selectColor(color)

        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.params.color).isEqualTo(color)
            Truth.assertThat(state.filterColors.find { it.color == color.value }?.isSelected)
                .isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test show filters when params are empty`() = testCoroutineDispatcher.runBlockingTest {
        // Given


        // When


        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.showFilters).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test show filters when params are non-empty`() = testCoroutineDispatcher.runBlockingTest {
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
        viewModel.search(query = "whatever")

        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.showFilters).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test clear query should reset filters`() = testCoroutineDispatcher.runBlockingTest {
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
        viewModel.search(query = "whatever")
        viewModel.selectOrientation(PhotoOrientation.VERTICAL)
        viewModel.search(query = "")

        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.params.areEmpty()).isTrue()
            Truth.assertThat(state.showFilters).isFalse()
            cancelAndIgnoreRemainingEvents()
        }

    }

}