package com.example.photodemoapplication.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.photodemoapplication.common.Resource
import com.example.photodemoapplication.data.local.ImageEntity
import com.example.photodemoapplication.domain.model.Image
import com.example.photodemoapplication.domain.model.ImageDetail
import com.example.photodemoapplication.domain.repository.PixabayRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakePixabayRepository() : PixabayRepository {


    private val imageList = mutableListOf<Image>()
    private lateinit var imageDetail: ImageDetail

    private val observableImageList = MutableLiveData<List<Image>>(imageList)

    private var shouldReturnNetworkError = false

    init {
        imageList.add(Image(1, "url", "book", "Parsa"))
        imageList.add(Image(2, "url", "pen", "Pouria"))
        imageList.add(Image(3, "url", "pencil", "Parham"))
        imageList.add(Image(4, "url", "notebook", "Mah"))
        imageList.add(Image(5, "url", "tape", "Hugan"))
    }

    fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    override suspend fun getImages(
        fetchFromRemote: Boolean,
        searchQuery: String
    ): Flow<Resource<List<Image>>> {
        return flow {
            if (shouldReturnNetworkError)
                emit(Resource.Error("Error", null))
            else
                emit(Resource.Success(imageList))
        }
    }

    override suspend fun getImageDetails(id: Int): Flow<Resource<ImageDetail>> {
        return flow {
            if (shouldReturnNetworkError)
                emit(Resource.Error("Error", null))
            else
                emit(Resource.Success(imageDetail))
        }
    }


}