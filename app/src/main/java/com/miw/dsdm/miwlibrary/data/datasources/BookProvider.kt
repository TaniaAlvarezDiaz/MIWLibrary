package com.miw.dsdm.miwlibrary.data.datasources

import com.miw.dsdm.miwlibrary.data.server.LibraryServer
import com.miw.dsdm.miwlibrary.data.storage.db.repositories.BookRepository
import com.miw.dsdm.miwlibrary.model.Book

object BookProvider {

    private val bookRepository: BookRepository = BookRepository()

    private val SOURCES = listOf(bookRepository, LibraryServer())

    /**
     * Function to get all books
     */
    fun requestAllBooks(): List<Book> {
        var res = emptyList<Book>()
        for (source in SOURCES) {
            res = source.requestAllBooks()
            if (!res.isNullOrEmpty()) break
        }
        return res
    }

    /**
     * Function to get the books that contain the category that is passed by parameter
     */
    fun requestBooksByCategory(categoryId: Long): List<Book> {
        return bookRepository.requestBooksByCategory(categoryId)
    }

    /**
     * Function to save in database the user's favorite book that is passed by parameter
     */
    fun saveFavoriteBook(userEmail: String, book: Book) {
        bookRepository.saveFavoriteBook(userEmail, book.id)
    }

    /**
     * Function to delete from database the user's favorite book that is passed by parameter
     */
    fun deleteFavoriteBook(userEmail: String, book: Book) {
        bookRepository.deleteFavoriteBook(userEmail, book.id)
    }

    /**
     * Function to know if book that is passed by parameter is favorite
     */
    fun isFavoriteBook(userEmail: String, book: Book): Boolean = bookRepository.isFavoriteBook(userEmail, book.id)

    /**
     * Function to get the user's favorite books that is passed by parameter
     */
    fun requestFavoritesBooksByUser(userEmail: String): List<Book> {
        return bookRepository.requestFavoritesBooksByUser(userEmail)
    }
}