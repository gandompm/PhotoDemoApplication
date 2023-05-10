package com.example.photodemoapplication.presentation.image_list

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _state = mutableStateOf(ImageListState())
    val state: State<ImageListState> = _state

    var isDialogShown by mutableStateOf(false)
        private set

    private var searchJob: Job? = null

    init {
        getImages()
    }

    fun onImageItemClick(imageId: Int){
        _state.value.selectedImageId = imageId
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
                _state.value = _state.value.copy(searchQuery = event.query)
                /**
                 * to avoid too many requests each time
                 * the user types a letter
                 * we have to consider a 500 ms delay
                 */
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    /**
                     * here we do the search in remote
                     *
                     * another strategy could be to only
                     * search in local data base
                     */
                    getImages(fetchFromRemote= true)
                }
            }
        }
    }

    private fun getImages(
        query: String = _state.value.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ){

        getImagesUseCase(fetchFromRemote, query).onEach {
            result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = ImageListState(
                            images = result.data ?: emptyList(),
                            searchQuery = query
                        )
                    }
                    is Resource.Error -> {
                        _state.value = ImageListState(
                            error = result.message ?: "An unexpected error occurred",
                            searchQuery = query
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = ImageListState(isLoading = true, searchQuery = query)
                    }
                }
        }.launchIn(viewModelScope)
    }
}