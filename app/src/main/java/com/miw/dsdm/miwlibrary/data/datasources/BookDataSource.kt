package com.miw.dsdm.miwlibrary.data.datasources

import com.miw.dsdm.miwlibrary.model.Book

interface BookDataSource {

    /**
     * Function to get all books
     */
    fun requestAllBooks(): List<Book>

    /**
     * Function to get the user's favorite books that is passed by parameter
     */
    fun requestFavoritesBooksByUser(userId: Long): List<Book>

    /**
     * Function to get the books that contain the category that is passed by parameter
     */
    fun requestBooksByCategory(categoryId: Long): List<Book>
}