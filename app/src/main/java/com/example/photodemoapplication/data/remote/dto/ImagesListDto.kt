package com.example.photodemoapplication.data.remote.dto

data class ImagesListDto(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)