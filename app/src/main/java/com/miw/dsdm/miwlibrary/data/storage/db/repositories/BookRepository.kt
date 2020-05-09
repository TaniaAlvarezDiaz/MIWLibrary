package com.miw.dsdm.miwlibrary.data.storage.db.repositories

import com.miw.dsdm.miwlibrary.data.datasources.BookDataSource
import com.miw.dsdm.miwlibrary.data.storage.db.LibraryDatabase
import com.miw.dsdm.miwlibrary.data.storage.db.dao.BookDao
import com.miw.dsdm.miwlibrary.data.storage.db.entities.BookCategoryEntity
import com.miw.dsdm.miwlibrary.data.storage.db.mappers.BookDataMapper
import com.miw.dsdm.miwlibrary.data.storage.db.mappers.CategoryDataMapper
import com.miw.dsdm.miwlibrary.model.Book

class BookRepository : BookDataSource {

    private val bookCategoryDao = LibraryDatabase.instance.bookCategoryDao()
    private val bookDao: BookDao = LibraryDatabase.instance.bookDao()

    override fun requestAllBooks(): List<Book> {
        //Get all books
        val booksDb = bookDao.getAll()
        val books = BookDataMapper.convertToDomain(booksDb)
        //Get books categories
        books.forEach {
            val categoriesDb = bookCategoryDao.getCategoriesByBook(it.id)
            it.categories = CategoryDataMapper.convertToDomain(categoriesDb)
        }
        return books
    }

    /**
     * Function to save the books that are passed by parameter in the database
     */
    fun saveBooks(books: List<Book>) {
        //Save books
        val booksDb = BookDataMapper.convertFromDomain(books)
        booksDb.forEach { bookDao.insert(it) }
        //Save books categories
        books.forEach { b ->
            b.categories.forEach {
                bookCategoryDao.insert(BookCategoryEntity(b.id, it.id))
            }
        }
    }

    /**
     * Function to get the user's favorite books that is passed by parameter
     */
    fun requestFavoritesBooksByUser(userEmail: String): List<Book> {
        TODO("Not yet implemented")
    }

    /**
     * Function to get the books that contain the category that is passed by parameter
     */
    fun requestBooksByCategory(categoryId: Long): List<Book> {
        TODO("Not yet implemented")
    }
}