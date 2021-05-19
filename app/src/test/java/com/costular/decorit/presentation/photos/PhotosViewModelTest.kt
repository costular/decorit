package com.costular.decorit.presentation.photos

import app.cash.turbine.test
import com.costular.decorit.core.TestDispatcherProvider
import com.costular.decorit.core.net.DispatcherProvider
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.interactor.GetViewPhotoQualityInteractor
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.PhotoQuality
import com.costular.decorit.domain.model.Photographer
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class PhotosViewModelTest {

    private val testCoroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val testDispatcherProvider: DispatcherProvider =
        TestDispatcherProvider(testCoroutineDispatcher)

    private val getPhotosInteractor: GetPhotosInteractor = mockk(relaxed = true)
    private val getViewPhotoQualityInteractor: GetViewPhotoQualityInteractor = mockk(relaxed = true)

    lateinit var photosViewModel: PhotosViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        every { getViewPhotoQualityInteractor.observe() } returns flowOf(PhotoQuality.Medium)

        photosViewModel = PhotosViewModel(
            testDispatcherProvider,
            getPhotosInteractor,
            getViewPhotoQualityInteractor
        )
    }

    @Before
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Test load`() = testCoroutineDispatcher.runBlockingTest {
        // Given
        val photos = listOf(
            Photo(
                "whatever",
                1080,
                1080,
                "pexels",
                Photographer(
                    "1",
                    "John Doe",
                    "avatar.jpg"
                ),
                false,
                "original.jpg",
                "large.jpg",
                "medium.jpg",
                "small.jpg"
            )
        )
        every { getViewPhotoQualityInteractor.observe() } returns flowOf(PhotoQuality.Medium)

        // When
        photosViewModel.load()

        // Then
        photosViewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.photos).isEqualTo(photos)
            Truth.assertThat(state.page).isEqualTo(1)
            cancelAndConsumeRemainingEvents()
        }
    }

}