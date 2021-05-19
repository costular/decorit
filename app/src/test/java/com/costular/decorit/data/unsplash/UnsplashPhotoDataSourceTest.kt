package com.costular.decorit.data.unsplash

import com.costular.decorit.data.SourceConstants.UNSPLASH
import com.costular.decorit.data.unsplash.model.*
import com.costular.decorit.domain.model.*
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class UnsplashPhotoDataSourceTest {

    private val testDispatcher = TestCoroutineScope()

    private val unsplashApi: UnsplashApi = mockk(relaxed = true)

    private lateinit var unsplashPhotoDataSource: UnsplashPhotoDataSource

    @Before
    fun setUp() {
        unsplashPhotoDataSource = UnsplashPhotoDataSource(unsplashApi)
    }

    @Test
    fun `Test get photos`() = testDispatcher.runBlockingTest {
        // Given
        val page = 1
        val perPage = 10
        val photos = listOf(
            UnsplashPhotoDTO(
                "1",
                1080,
                1080,
                UnsplashUserDTO(
                    "1",
                    "john",
                    UnsplashProfileImageDTO(
                        "",
                        "avatar",
                        ""
                    )
                ),
                UnsplashUrlsDTO(
                    "original",
                    "full",
                    "regular",
                    "small"
                )
            )
        )
        val expected = listOf(
            Photo(
                "unsplash-1",
                1080,
                1080,
                UNSPLASH,
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

        coEvery { unsplashApi.getPhotos(page, perPage) } returns photos

        // When
        val result = unsplashPhotoDataSource.getPhotos(page, perPage)

        // Then
        Truth.assertThat(result.first().id).isEqualTo(expected.first().id)
    }

    @Test
    fun `Test search photos`() = testDispatcher.runBlockingTest {
        // Given
        val searchParams = SearchParams(
            query = "whatever",
            color = PhotoColor(0, ColorValue.YELLOW),
            PhotoOrientation.VERTICAL
        )

        val page = 1
        val perPage = 10
        val photos = listOf(
            UnsplashPhotoDTO(
                "1",
                1080,
                1080,
                UnsplashUserDTO(
                    "1",
                    "john",
                    UnsplashProfileImageDTO(
                        "",
                        "avatar",
                        ""
                    )
                ),
                UnsplashUrlsDTO(
                    "original",
                    "full",
                    "regular",
                    "small"
                )
            )
        )
        val expected = listOf(
            Photo(
                "unsplash-1",
                1080,
                1080,
                UNSPLASH,
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

        coEvery {
            unsplashApi.searchPhotos(
                page,
                perPage,
                "whatever",
                "yellow",
                "portrait"
            )
        } returns UnsplashSearchResults(10, 1, photos)

        // When
        val result = unsplashPhotoDataSource.getPhotos(page, perPage, searchParams)

        // Then
        Truth.assertThat(result.first().id).isEqualTo(expected.first().id)
    }

}