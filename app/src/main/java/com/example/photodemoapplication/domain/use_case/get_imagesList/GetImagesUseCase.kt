package com.example.photodemoapplication.domain.use_case.get_imagesList

import com.example.photodemoapplication.common.Resource
import com.example.photodemoapplication.data.remote.dto.ImagesListDto
import com.example.photodemoapplication.data.remote.dto.toImage
import com.example.photodemoapplication.domain.model.Image
import com.example.photodemoapplication.domain.repository.PixabayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/**
 * a use case for getting list of images
 *
 * injecting repository dependency in the constructor
 */
class GetImagesUseCase @Inject constructor(
    private val repository: PixabayRepository
) {

    operator fun invoke(
        fetchFromRemote: Boolean = false,
        searchQuery: String = ""
        ) : Flow<Resource<List<Image>>> =

        flow {
            try {
                emit(Resource.Loading<List<Image>>())
                repository.getImages(fetchFromRemote, searchQuery).collect{
                    result ->
                    emit(result)
                }
            }
            // if we get a response code that doesn't start with 2
            catch (e: HttpException){
                emit(Resource.Error<List<Image>>(e.localizedMessage ?: "An unexpected error occurred"))
            }
            // if our repo can not talk to remote api, e.g. internet connection error
            catch (e: IOException){
                emit(Resource.Error<List<Image>>("Couldn't reach server. Check your connection"))
            }
        }
}