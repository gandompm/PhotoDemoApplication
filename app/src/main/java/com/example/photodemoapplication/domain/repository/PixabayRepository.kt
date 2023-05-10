package com.example.photodemoapplication.domain.repository

import com.example.photodemoapplication.common.Resource
import com.example.photodemoapplication.data.remote.dto.ImagesListDto
import com.example.photodemoapplication.domain.model.Image
import com.example.photodemoapplication.domain.model.ImageDetail
import kotlinx.coroutines.flow.Flow

/**
 * functions that we want to have
 * from either the api
 * or the data base
 */
interface PixabayRepository {

    suspend fun getImages(
        fetchFromRemote: Boolean,
        searchQuery: String )
    : Flow<Resource<List<Image>>>

    suspend fun getImageDetails(id: Int) : Flow<Resource<ImageDetail>>
}