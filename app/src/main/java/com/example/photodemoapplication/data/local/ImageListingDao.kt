package com.example.photodemoapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ImageListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImageListing(
        imageListingEntities: List<ImageEntity>
    )

    @Query("DELETE FROM imageentity")
    suspend fun clearImageListing()

    @Query(
        """
            SELECT * 
            FROM imageentity
            WHERE LOWER(tags) LIKE '%' || LOWER(:query) || '%' OR
                LOWER(:query) == user
        """
    )
    suspend fun searchImageListing(query: String): List<ImageEntity>
}