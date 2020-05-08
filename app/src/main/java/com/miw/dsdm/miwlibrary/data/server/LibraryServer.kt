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

    override fun requestAllBooks(): List<Book> {
        TODO("Not yet implemented")
        //val response = RetrofitBuilder.apiService.getBooks(NUM_BOOKS_FROM_SERVER)
    }

    override fun requestAllCategories(): List<Category> {
        TODO("Not yet implemented")
    }
}