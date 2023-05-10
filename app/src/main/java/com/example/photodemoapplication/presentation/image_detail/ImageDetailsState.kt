package com.example.photodemoapplication.presentation.image_detail

import com.example.photodemoapplication.domain.model.Image
import com.example.photodemoapplication.domain.model.ImageDetail

data class ImageDetailsState(
    val isLoading: Boolean = false,
    val imageDetail: ImageDetail ?= null,
    val error: String = ""
)
