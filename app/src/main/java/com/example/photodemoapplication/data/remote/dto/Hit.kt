package com.example.photodemoapplication.data.remote.dto

import com.example.photodemoapplication.domain.model.Image
import com.example.photodemoapplication.domain.model.ImageDetail

data class Hit(
    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val user_id: Int,
    val views: Int,
    val webformatHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int
)

fun Hit.toImage() : Image{
    return Image(
        id = id,
        previewURL = previewURL,
        tags = tags,
        user = user
    )
}

fun Hit.toImageDetail() : ImageDetail{
    return ImageDetail(
        id = id,
        largeImageURL = largeImageURL,
        tags = tags,
        user = user,
        likes = likes,
        downloads = downloads,
        comments = comments
    )
}