package com.example.photodemoapplication.presentation.image_list


sealed class ImageListEvents {
    object Refresh: ImageListEvents()
    data class OnSearchQueryChange(val query: String): ImageListEvents()
}