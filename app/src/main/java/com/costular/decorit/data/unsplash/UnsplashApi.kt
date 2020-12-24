package com.costular.decorit.data.unsplash

import com.costular.decorit.data.unsplash.model.UnsplashPhotoDTO
import com.costular.decorit.data.unsplash.model.UnsplashSearchResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("order_by") orderBy: String = "latest"
    ): List<UnsplashPhotoDTO>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("query") query: String?,
        @Query("color") color: String?,
        @Query("orientation") orientation: String?
    ): UnsplashSearchResults

    @GET("photos/{id}")
    suspend fun getPhotoById(@Path("id") id: String): UnsplashPhotoDTO

}