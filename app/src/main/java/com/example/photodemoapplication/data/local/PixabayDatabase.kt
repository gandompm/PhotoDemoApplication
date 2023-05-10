package com.example.photodemoapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ImageEntity::class],
    version = 1
)
abstract class PixabayDatabase : RoomDatabase() {

    abstract val dao: ImageListingDao
}