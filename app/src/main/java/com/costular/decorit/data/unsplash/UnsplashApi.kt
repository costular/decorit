package com.costular.decorit.data.unsplash

import com.costular.decorit.data.unsplash.model.UnsplashPhotoDTO
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

    @GET("photos/{id}")
    suspend fun getPhotoById(@Path("id") id: String): UnsplashPhotoDTO

}