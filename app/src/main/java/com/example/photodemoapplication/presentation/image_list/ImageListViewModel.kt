package com.example.photodemoapplication.presentation.image_list

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photodemoapplication.common.Constants
import com.example.photodemoapplication.common.Resource
import com.example.photodemoapplication.domain.use_case.get_imagesList.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel(){

    var state by mutableStateOf(ImageListState())

    var isDialogShown by mutableStateOf(false)
        private set

    private var searchJob: Job? = null

    init {
        getImages()
    }

    fun onImageItemClick(imageId: Int){
        state.selectedImageId = imageId
        isDialogShown = true
    }

    fun onDismissDialog(){
        isDialogShown = false
    }

    fun onEvent(event: ImageListEvents){
        when(event){
            is ImageListEvents.Refresh -> {
                getImages(fetchFromRemote = true)
            }
            is ImageListEvents.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                /**
                 * to avoid too many requests each time
                 * the user types a letter
                 * we have to consider a 500 ms delay
                 */
                if (state.searchQuery.length >= Constants.MIN_SEARCH_QUERY_LENGTH){

                    searchJob?.cancel()
                    searchJob = viewModelScope.launch {
                        delay(500L)
                        /**
                         * here we do the search first in local
                         * and then in remote once we get the
                         * response from remote api
                         */
                        getImages(fetchFromRemote = true)
                    }
                }
            }
        }
    }

    private fun getImages(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ){

        getImagesUseCase(fetchFromRemote, query).onEach {
            result ->
                when(result){
                    is Resource.Success -> {
                        state = state.copy(
                            images = result.data ?: emptyList()
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
        }.launchIn(viewModelScope)
    }
}