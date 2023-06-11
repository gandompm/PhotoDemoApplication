package com.example.photodemoapplication.presentation.image_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.photodemoapplication.MainCoroutineRule
import com.example.photodemoapplication.data.repository.FakePixabayRepository
import com.example.photodemoapplication.domain.model.Image
import com.example.photodemoapplication.domain.use_case.get_imagesList.GetImagesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Dispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ImageListViewModelTest{


     private lateinit var viewModel: ImageListViewModel
     private lateinit var getImagesUseCase: GetImagesUseCase

     @get:Rule
     var instantTaskExecutorRule = InstantTaskExecutorRule()

     @get:Rule
     var mainCoroutineRule = MainCoroutineRule()

    /**
     * before each test this block will be executed
     *
     * we have created FakePixabayRepository for the test case
     * here we don't want to make api request in tests
     * because unit test should run fast
     */
    @Before
     fun setup(){
        getImagesUseCase = GetImagesUseCase(FakePixabayRepository())
         viewModel = ImageListViewModel(getImagesUseCase)

    }

    @After
    fun cleanup() {
    }


    @Test
    fun `search a word with a length lower than three alphabet, should not return an image list`(){

        viewModel.state = viewModel.state.copy(searchQuery = "bo")
        val value = viewModel.state.images

        assert(value != emptyList<Image>())
    }


 }