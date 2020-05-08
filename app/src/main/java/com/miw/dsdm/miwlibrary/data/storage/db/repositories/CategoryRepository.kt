package com.miw.dsdm.miwlibrary.data.storage.db.repositories

import com.miw.dsdm.miwlibrary.data.datasources.CategoryDataSource
import com.miw.dsdm.miwlibrary.data.storage.db.LibraryDatabase
import com.miw.dsdm.miwlibrary.data.storage.db.dao.CategoryDao
import com.miw.dsdm.miwlibrary.data.storage.db.mappers.CategoryDataMapper
import com.miw.dsdm.miwlibrary.model.Category

class CategoryRepository : CategoryDataSource {

    private val bookCategoryDao = LibraryDatabase.instance.bookCategoryDao()
    private val categoryDao: CategoryDao = LibraryDatabase.instance.categoryDao()

    override fun requestAllCategories(): List<Category> {
        //Get all categories
        val categoriesDb = categoryDao.getAll()
        return CategoryDataMapper.convertToDomain(categoriesDb)
    }

    /**
     * Function to save the categories that are passed by parameter in the database
     */
    fun saveCategories(categories: List<Category>) {
        val categoriesDb = CategoryDataMapper.convertFromDomain(categories)
        categoriesDb.forEach { categoryDao.insert(it) }
    }

    /**
     * Function to get all the categories of the book that is passed by parameter
     */
    fun requestCategoriesByBook(bookId: Long): List<Category> {
        val categoriesDb = bookCategoryDao.getCategoriesByBook(bookId)
        return CategoryDataMapper.convertToDomain(categoriesDb)
    }
}