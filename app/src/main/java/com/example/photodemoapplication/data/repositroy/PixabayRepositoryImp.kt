package com.example.photodemoapplication.data.repositroy

import com.example.photodemoapplication.common.Resource
import com.example.photodemoapplication.data.local.PixabayDatabase
import com.example.photodemoapplication.data.local.toImage
import com.example.photodemoapplication.data.remote.PixabayApi
import com.example.photodemoapplication.data.remote.dto.ImagesListDto
import com.example.photodemoapplication.data.remote.dto.toImage
import com.example.photodemoapplication.data.remote.dto.toImageDetail
import com.example.photodemoapplication.domain.model.Image
import com.example.photodemoapplication.domain.model.ImageDetail
import com.example.photodemoapplication.domain.model.toImageEntity
import com.example.photodemoapplication.domain.repository.PixabayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * repository implementation
 *
 * it will handle whether to get data from remote or local database
 */
class PixabayRepositoryImp @Inject constructor(
    private val api: PixabayApi,
    private val db: PixabayDatabase
): PixabayRepository {

    override suspend fun getImages(
        fetchFromRemote: Boolean,
        searchQuery: String): Flow<Resource<List<Image>>> {

        return flow {

            // receive data from local database
            val localImages = db.dao.searchImageListing(searchQuery)
            val isDbEmpty = localImages.isEmpty()
            emit(Resource.Success<List<Image>>(
                localImages.map {
                    it.toImage()
                }
            ))


            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache) {
                return@flow
            }

            // receive data from remote
            val remoteResponse = api.getImages(searchQuery).hits


            remoteResponse.let { listings ->
                db.dao.clearImageListing()
                db.dao.insertImageListing(
                    listings.map { it.toImage().toImageEntity()}
                )
                emit(Resource.Success<List<Image>>(
                    data = db.dao
                        .searchImageListing("")
                        .map { it.toImage() }
                ))
            }
        }
    }

    override suspend fun getImageDetails(id: Int): Flow<Resource<ImageDetail>> {
        return flow {
            val remoteResponse = api.getImageDetails(id).hits[0].toImageDetail()
            emit(Resource.Success<ImageDetail>(remoteResponse))
        }
    }
}