package com.example.photodemoapplication.presentation.image_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photodemoapplication.common.Constants
import com.example.photodemoapplication.common.Resource
import com.example.photodemoapplication.domain.use_case.get_imageDetails.GetImageDetailsUseCase
import com.example.photodemoapplication.domain.use_case.get_imagesList.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    // savedStateHandle will contain navigation parameters
    private val savedStateHandle: SavedStateHandle,
    private val getImageDetailsUseCase: GetImageDetailsUseCase) : ViewModel(){

    private val _state = mutableStateOf(ImageDetailsState())
    val state: State<ImageDetailsState> = _state

    init {
        savedStateHandle.get<Int>("imageId")?.let { coinId ->
            getImageDetails(coinId)
        }
    }



    private fun getImageDetails(imageId: Int){

        getImageDetailsUseCase(imageId).onEach {
            result ->
                when(result){
                    is Resource.Success -> {
                        _state.value = ImageDetailsState(
                            imageDetail = result.data,
                        )
                    }
                    is Resource.Error -> {
                        _state.value = ImageDetailsState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = ImageDetailsState(isLoading = true)
                    }
                }
        }.launchIn(viewModelScope)
    }
}