package com.costular.decorit.data.repository

import com.costular.decorit.data.unsplash.UnsplashPhotoDataSource
import com.costular.decorit.domain.repository.PhotoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class DefaultPhotoRepositoryTest {

    private val testDispatcher = TestCoroutineScope()

    private lateinit var repository: PhotoRepository

    private val unsplashPhotoDataSource: UnsplashPhotoDataSource = mockk(relaxed = true)

    @Before
    fun setUp() {
        repository = DefaultPhotoRepository(unsplashPhotoDataSource)
    }

    @Test
    fun `Test get photos merging sources`() = testDispatcher.runBlockingTest {
        // Given
        val page = 1
        val perPage = 20

        coEvery { unsplashPhotoDataSource.getPhotos(any(), any(), any()) } returns listOf()

        // When
        repository.getPhotos(page, perPage)

        // Then
        coVerify { unsplashPhotoDataSource.getPhotos(page, perPage) }
    }

}