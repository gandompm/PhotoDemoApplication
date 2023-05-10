package com.example.photodemoapplication.domain.model

data class ImageDetail (
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val largeImageURL: String,
    val likes: Int,
    val tags: String,
    val user: String
)