package com.miw.dsdm.miwlibrary.data.server

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miw.dsdm.miwlibrary.data.datasources.BookDataSource
import com.miw.dsdm.miwlibrary.data.datasources.CategoryDataSource
import com.miw.dsdm.miwlibrary.data.server.mappers.BookServerMapper
import com.miw.dsdm.miwlibrary.data.server.mappers.CategoryServerMapper
import com.miw.dsdm.miwlibrary.data.storage.db.repositories.BookRepository
import com.miw.dsdm.miwlibrary.data.storage.db.repositories.CategoryRepository
import com.miw.dsdm.miwlibrary.model.Book
import com.miw.dsdm.miwlibrary.model.Category
import com.miw.dsdm.miwlibrary.utils.BASE_URL_SERVER
import com.miw.dsdm.miwlibrary.utils.NUM_BOOKS_FROM_SERVER
import java.net.URL

class LibraryServer: BookDataSource, CategoryDataSource {

    //Repositories
    private val bookRepository: BookRepository = BookRepository()
    private val categoryRepository: CategoryRepository = CategoryRepository()

    /**
     * Function to get all books
     */
    override fun requestAllBooks(): List<Book> {
        //Call service
        val booksJsonStr = URL("$BASE_URL_SERVER?num_items=$NUM_BOOKS_FROM_SERVER").readText()
        //Convert into domain object
        val booksType = object : TypeToken<List<BookResponse>>() {}.type
        val result = Gson().fromJson<List<BookResponse>>(booksJsonStr, booksType)
        val books = BookServerMapper.convertToDomain(result)
        //Save books in database
        bookRepository.saveBooks(books)
        return books
    }

    /**
     * Function to get all book categories
     */
    override fun requestAllCategories(): List<Category> {
        //Call service
        val categoriesJsonStr = URL("$BASE_URL_SERVER?get_categories").readText()
        //Convert into domain object
        val categoriesType = object : TypeToken<List<CategoryResponse>>() {}.type
        val result = Gson().fromJson<List<CategoryResponse>>(categoriesJsonStr, categoriesType)
        val categories = CategoryServerMapper.convertToDomain(result)
        //Save categories in database
        categoryRepository.saveCategories(categories)
        return categories
    }
}