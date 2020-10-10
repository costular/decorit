package com.costular.decorit.presentation.photos

import com.costular.decorit.data.SourceConstants
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer
import com.costular.decorit.presentation.ReduxViewModelTest
import com.costular.decorit.presentation.base.ReduxViewModel
import com.costular.decorit.presentation.testBlocking
import com.costular.decorit.util.PhotoId
import com.costular.decorit.util.PhotographerId
import com.costular.decorit.util.SourceId
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.uniflow.android.test.TestViewObserver
import io.uniflow.android.test.createTestObserver
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PhotosViewModelTest : ReduxViewModelTest() {

    private lateinit var photosViewModel: PhotosViewModel
    lateinit var view: TestViewObserver

    private val getPhotosInteractor: GetPhotosInteractor = mockk(relaxed = true)

    @Before
    fun setUp() {
        photosViewModel = PhotosViewModel(dispatcherProvider, getPhotosInteractor)
        view = photosViewModel.createTestObserver()
    }

    @Test
    fun `Test load`() = testBlocking {
        // Given
        val photos = listOf(
            Photo(
                PhotoId("1"),
                1080,
                1080,
                SourceId(SourceConstants.UNSPLASH),
                Photographer(
                    PhotographerId("1"),
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
        photosViewModel.load()

        // Then
        Truth.assertThat(view.lastStateOrNull).isEqualTo(
            PhotosState(
                2,
                photos
            )
        )
    }

}