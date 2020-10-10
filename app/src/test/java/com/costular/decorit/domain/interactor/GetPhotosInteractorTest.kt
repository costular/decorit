package com.costular.decorit.domain.interactor

import com.costular.decorit.domain.model.Photo
import com.costular.decorit.domain.model.Photographer
import com.costular.decorit.domain.model.SearchParams
import com.costular.decorit.domain.repository.PhotoRepository
import com.costular.decorit.util.PhotoId
import com.costular.decorit.util.PhotographerId
import com.costular.decorit.util.SourceId
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class GetPhotosInteractorTest {

    private val testDispatcher = TestCoroutineScope()

    private lateinit var getPhotosInteractor: GetPhotosInteractor

    private val repo: PhotoRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        getPhotosInteractor = GetPhotosInteractor(repo)
    }

    @Test
    fun `Get photos interactor`() = testDispatcher.runBlockingTest {
        // Given
        val page = 1
        val perPage = 20
        val searchParams = SearchParams()
        val photos = listOf(
            Photo(
                PhotoId("1"),
                1080,
                1080,
                SourceId("source"),
                Photographer(
                    PhotographerId("photographer"),
                    "John",
                    "costular.com/avatar.png"
                ),
                false,
                "original",
                "large",
                "medium",
                "small"
            )
        )

        coEvery { repo.getPhotos(page, perPage, searchParams) } returns photos

        // When
        val result = repo.getPhotos(page, perPage, searchParams)

        // Then
        Truth.assertThat(result).isEqualTo(photos)
    }

}