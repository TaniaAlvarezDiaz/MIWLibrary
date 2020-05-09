package com.miw.dsdm.miwlibrary.data.server

import com.miw.dsdm.miwlibrary.data.datasources.BookDataSource
import com.miw.dsdm.miwlibrary.data.datasources.CategoryDataSource
import com.miw.dsdm.miwlibrary.data.storage.db.repositories.BookRepository
import com.miw.dsdm.miwlibrary.data.storage.db.repositories.CategoryRepository
import com.miw.dsdm.miwlibrary.model.Book
import com.miw.dsdm.miwlibrary.model.Category
import com.miw.dsdm.miwlibrary.utils.NUM_BOOKS_FROM_SERVER

class LibraryServer: BookDataSource, CategoryDataSource {

    //Repositories
    private val bookRepository: BookRepository = BookRepository()
    private val categoryRepository: CategoryRepository = CategoryRepository()

    /**
     * Function to get all books
     */
    override fun requestAllBooks(): List<Book> {
        TODO("Not yet implemented")
        //TODO call service, then save books in database
        //val response = RetrofitBuilder.apiService.getBooks(NUM_BOOKS_FROM_SERVER)
    }

    /**
     * Function to get all book categories
     */
    override fun requestAllCategories(): List<Category> {
        TODO("Not yet implemented")
    }
}