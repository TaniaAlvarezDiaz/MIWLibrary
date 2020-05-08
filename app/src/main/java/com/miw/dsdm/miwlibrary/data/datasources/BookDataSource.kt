package com.miw.dsdm.miwlibrary.data.datasources

import com.miw.dsdm.miwlibrary.model.Book

interface BookDataSource {

    /**
     * Function to get all books
     */
    fun requestAllBooks(): List<Book>
}