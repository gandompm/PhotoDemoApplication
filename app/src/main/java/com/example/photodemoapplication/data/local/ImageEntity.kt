package com.example.photodemoapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.photodemoapplication.domain.model.Image


@Entity
data class ImageEntity (
    @PrimaryKey val id: Int,
    val previewURL: String,
    val tags: String,
    val user: String,
)

fun ImageEntity.toImage(): Image {
    return Image(
        id = id,
        previewURL = previewURL,
        tags = tags,
        user = user
    )
}