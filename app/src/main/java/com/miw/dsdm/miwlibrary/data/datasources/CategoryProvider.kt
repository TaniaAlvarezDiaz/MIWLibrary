package com.miw.dsdm.miwlibrary.data.datasources

import com.miw.dsdm.miwlibrary.data.server.LibraryServer
import com.miw.dsdm.miwlibrary.data.storage.db.repositories.CategoryRepository
import com.miw.dsdm.miwlibrary.model.Category

object CategoryProvider {

    private val categoryRepository : CategoryRepository = CategoryRepository()

    private val SOURCES = listOf(categoryRepository, LibraryServer())

    /**
     * Function to get all book categories
     */
    fun requestAllCategories(): List<Category> {
        var res = emptyList<Category>()
        for (source in SOURCES) {
            res = source.requestAllCategories()
            if (!res.isNullOrEmpty()) break
        }
        return res
    }

    /**
     * Function to save the categories that are passed by parameter in the database
     */
    fun saveCategories(categories: List<Category>) {
        categoryRepository.saveCategories(categories)
    }

    /**
     * Function to get all the categories of the book that is passed by parameter
     */
    fun requestCategoriesByBook(bookId: Long): List<Category> {
        return categoryRepository.requestCategoriesByBook(bookId)
    }
}