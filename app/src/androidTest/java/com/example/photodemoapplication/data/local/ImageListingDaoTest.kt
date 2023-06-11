package com.example.photodemoapplication.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
// we tell junit by this that these tests are instrumented tests
@RunWith(AndroidJUnit4::class)
// we tell junit by this command that these are unit tests
@SmallTest
class ImageListingDaoTest {

    private lateinit var database: PixabayDatabase
    private lateinit var dao: ImageListingDao

    /**
     * we need to explicitly tell junit
     * that we want to execute all the functions
     * one after the other
     * in the same thread
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /*
    we want to make sure that we have entirely new database
    for each test case

    because it should be independent
    so the initialization should happen inside @before
    it will be executed before each test case
     */
    @Before
    fun setup(){
        // the difference between DatabaseBuilder and
        // inMemoryDatabaseBuilder is that the second one
        // it will only hold data in the ram not in the
        // persistence database
        // because we need to store it only for that test
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PixabayDatabase::class.java
        )
                // if we apply this function, we allow it to create
                // it in the main thread
                // because in test cases we want that to be executed
                // in the main thread to prevent manipulating data
                // that could happen by multithreading
            .allowMainThreadQueries()
            .build()

        dao = database.dao
    }

    // it will be executed after each test
    @After
    fun teardown(){
        database.close()
    }

    // since we don't want multithreading,
    // we will use runBlocking for the coroutine here
    // that is a way to use coroutine in the main thread
    @Test
    fun insertImageListing() = runBlockingTest {
        val imageItem = ImageEntity(1, "url", "book", "Parsa")
        val imageList = mutableListOf<ImageEntity>(imageItem)

        dao.insertImageListing(imageList)

        /*
        we have a problem here
        because the searchImageListing() will return
        a live data and not the actual list

        live data runs asynchronous and we don't want that
        in our test case

        but luckily there is a helpful google class
        this class will provide a way that wait until
        the live data returns a value
         */
        val allImageItems = dao.searchImageListing("book")

        assert(allImageItems.contains(imageItem))
    }

    @Test
    fun deleteImageListing() = runBlockingTest {
        val imageItem = ImageEntity(1, "url", "book", "Parsa")
        dao.insertImageListing(mutableListOf<ImageEntity>(imageItem))
        dao.clearImageListing()

        val allImageItems = dao.searchImageListing("book")

        assert(!allImageItems.contains(imageItem))
    }

    @Test
    fun searchingFunctionalityInDataBase() = runBlockingTest {
        val imageList = mutableListOf<ImageEntity>(
            ImageEntity(1, "url", "book", "Parsa"),
            ImageEntity(2, "url", "pencil", "Parham"),
            ImageEntity(3, "url", "book", "Pouria"),
            ImageEntity(4, "url", "chips", "Hanay")
        )

        dao.insertImageListing(imageList)

        val bookImageList = dao.searchImageListing("book")
        assert(bookImageList.size == 2)
    }

    @Test
    fun searchingFunctionality2InDataBase() = runBlockingTest {
        val imageList = mutableListOf<ImageEntity>(
            ImageEntity(1, "url", "book", "Parsa"),
            ImageEntity(2, "url", "pencil", "Parham"),
            ImageEntity(3, "url", "book", "Pouria"),
            ImageEntity(4, "url", "chips", "Hanay")
        )

        dao.insertImageListing(imageList)

        val bookImageList = dao.searchImageListing("boo")
        assert(bookImageList.size == 2)
    }

    @Test
    fun searchingFunctionalityWhenThereIsMoreThanOneTagInDataBase() = runBlockingTest {
        val imageList = mutableListOf<ImageEntity>(
            ImageEntity(1, "url", "book", "Parsa"),
            ImageEntity(2, "url", "pencil, book", "Parham"),
            ImageEntity(3, "url", "book", "Pouria"),
            ImageEntity(4, "url", "chips", "Hanay")
        )

        dao.insertImageListing(imageList)

        val bookImageList = dao.searchImageListing("book")
        assert(bookImageList.size == 3)
    }

    @Test
    fun searchingUserFunctionalityInDataBase() = runBlockingTest {
        val imageList = mutableListOf<ImageEntity>(
            ImageEntity(1, "url", "book", "Parsa"),
            ImageEntity(2, "url", "pencil, book", "Parham"),
            ImageEntity(3, "url", "book", "Pouria"),
            ImageEntity(4, "url", "chips", "Hanay")
        )

        dao.insertImageListing(imageList)

        val bookImageList = dao.searchImageListing("Parham")
        assert(bookImageList.size == 1)
    }

    @Test
    fun searchingFunctionalityForAssertingNotConsideringLowerCaseSearchQueryInDataBase() = runBlockingTest {
        val imageList = mutableListOf<ImageEntity>(
            ImageEntity(1, "url", "book", "Parsa"),
            ImageEntity(2, "url", "pencil", "Parham"),
            ImageEntity(3, "url", "book", "Pouria"),
            ImageEntity(4, "url", "chips", "Hanay")
        )

        dao.insertImageListing(imageList)

        val bookImageList = dao.searchImageListing("BOOK")
        assert(bookImageList.size == 2)
    }
}