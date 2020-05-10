package com.miw.dsdm.miwlibrary.data.server

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.miw.dsdm.miwlibrary.data.datasources.BookDataSource
import com.miw.dsdm.miwlibrary.data.server.mappers.BookServerMapper
import com.miw.dsdm.miwlibrary.data.storage.db.repositories.BookRepository
import com.miw.dsdm.miwlibrary.model.Book
import com.miw.dsdm.miwlibrary.utils.BASE_URL_SERVER
import com.miw.dsdm.miwlibrary.utils.NUM_BOOKS_FROM_SERVER
import java.net.URL

class LibraryServer : BookDataSource {

    //Repositories
    private val bookRepository: BookRepository = BookRepository()

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
}