package com.costular.decorit.presentation.photos

/*
import com.costular.decorit.data.SourceConstants
import com.costular.decorit.domain.interactor.GetPhotosInteractor
import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer
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
        photosViewModel.load()

        // Then
        Truth.assertThat(view.lastStateOrNull).isEqualTo(
            PhotosState(
                2,
                photos
            )
        )
    }

    @Test
    fun `Test open photo`() = testBlocking {
        // Given
        val photo = Photo(
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

        // When
        photosViewModel.openPhoto(photo)

        // Then
        Truth.assertThat(PhotosEvents.OpenPhoto(photo))
    }

}
 */