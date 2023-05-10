package com.example.photodemoapplication.presentation.image_list

import com.example.photodemoapplication.domain.model.Image

data class ImageListState(
    val isLoading: Boolean = false,
    val images: List<Image> = emptyList(),
    val error: String = "fruits",
    val searchQuery: String = "",
    var selectedImageId: Int = 0
)
