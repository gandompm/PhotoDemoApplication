package com.example.photodemoapplication.data.remote

import com.example.photodemoapplication.common.Constants
import com.example.photodemoapplication.data.remote.dto.Hit
import com.example.photodemoapplication.data.remote.dto.ImagesListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * retrofit api interface
 *
 * defining the different functions that we want to have access from the api
 */
interface PixabayApi {

    @GET("?key=${Constants.API_KEY}&image_type=photo")
    suspend fun getImages(@Query("q") searchKey: String): ImagesListDto

    @GET("?key=${Constants.API_KEY}")
    suspend fun getImageDetails(@Query("id") id: Int) : ImagesListDto
}