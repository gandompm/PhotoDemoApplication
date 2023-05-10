package com.example.photodemoapplication.domain.model

import com.example.photodemoapplication.data.local.ImageEntity


/**
 * we don't need all the data from Hit class
 *
 * this is the data class that we use to display a specific image in our list
 * with all attributes that we want to display later
 */
data class Image (
    val id: Int,
    val previewURL: String,
    val tags: String,
    val user: String
)


fun Image.toImageEntity(): ImageEntity {
    return ImageEntity(
        id = id,
        previewURL = previewURL,
        tags = tags,
        user = user
    )
}