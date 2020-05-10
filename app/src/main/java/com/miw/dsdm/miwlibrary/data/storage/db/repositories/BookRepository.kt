package com.miw.dsdm.miwlibrary.data.storage.db.repositories

import com.miw.dsdm.miwlibrary.data.datasources.BookDataSource
import com.miw.dsdm.miwlibrary.data.storage.db.LibraryDatabase
import com.miw.dsdm.miwlibrary.data.storage.db.dao.BookDao
import com.miw.dsdm.miwlibrary.data.storage.db.dao.FavoriteDao
import com.miw.dsdm.miwlibrary.data.storage.db.entities.BookEntity
import com.miw.dsdm.miwlibrary.data.storage.db.entities.FavoriteEntity
import com.miw.dsdm.miwlibrary.data.storage.db.mappers.BookDataMapper
import com.miw.dsdm.miwlibrary.model.Book

class BookRepository : BookDataSource {

    private val favoriteDao: FavoriteDao = LibraryDatabase.instance.favoriteDao()
    private val bookDao: BookDao = LibraryDatabase.instance.bookDao()

    /**
     * Function to get all books
     */
    override fun requestAllBooks(): List<Book> {
        //Get all books
        val booksDb = bookDao.getAll()
        return convertBooks(booksDb)
    }

    /**
     * Function to save the books that are passed by parameter in the database
     */
    fun saveBooks(books: List<Book>) {
        //Save books
        val booksDb = BookDataMapper.convertFromDomain(books)
        booksDb.forEach { bookDao.insert(it) }
    }

    /**
     * Function to save in database the user's favorite book that is passed by parameter
     */
    fun saveFavoriteBook(userEmail: String, bookId: Long) {
        //Save favorite book
        favoriteDao.insert(FavoriteEntity(userEmail, bookId))
    }

    /**
     * Function to delete from database the user's favorite book that is passed by parameter
     */
    fun deleteFavoriteBook(userEmail: String, bookId: Long) {
        favoriteDao.delete(FavoriteEntity(userEmail, bookId))
    }

    /**
     * Function to know if book that is passed by parameter is favorite
     */
    fun isFavoriteBook(userEmail: String, bookId: Long): Boolean {
        val favorite = favoriteDao.existsFavoriteBook(userEmail, bookId)
        if (favorite != null) return true
        return false
    }

    /**
     * Function to get the user's favorite books that is passed by parameter
     */
    fun requestFavoritesBooksByUser(userEmail: String): List<Book> {
        val booksDb = favoriteDao.getFavoritesBooksByUser(userEmail)
        return convertBooks(booksDb)
    }

    /**
     * Function to convert each book in the list that is passed by parameter into a domain object
     */
    private fun convertBooks(booksDb: List<BookEntity>): List<Book> = BookDataMapper.convertToDomain(booksDb)
}