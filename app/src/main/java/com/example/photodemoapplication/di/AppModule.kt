package com.example.photodemoapplication.di

import android.app.Application
import androidx.compose.ui.unit.Constraints
import androidx.room.Room
import com.example.photodemoapplication.common.Constants
import com.example.photodemoapplication.data.local.PixabayDatabase
import com.example.photodemoapplication.data.remote.PixabayApi
import com.example.photodemoapplication.data.repositroy.PixabayRepositoryImp
import com.example.photodemoapplication.domain.repository.PixabayRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



/**
 * we tell dagger hill
 * this is how you can create dependencies
 *
 * we want to avoid hardcoding dependencies into our objects
 *
 * we inject the dependency from the outside, then we can easily swap dependency
 * if we want to use the fake repo, or the implementation
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePixabayApi() : PixabayApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PixabayApi::class.java)
    }

    @Provides
    @Singleton
    fun providePixabayDatabase(app: Application): PixabayDatabase {
        return Room.databaseBuilder(
            app,
            PixabayDatabase::class.java,
            "pixabay.db"
        ).build()
    }


    @Provides
    @Singleton
    fun providePixabayRepository(api : PixabayApi, db : PixabayDatabase): PixabayRepository{
        return PixabayRepositoryImp(api, db)
    }

}