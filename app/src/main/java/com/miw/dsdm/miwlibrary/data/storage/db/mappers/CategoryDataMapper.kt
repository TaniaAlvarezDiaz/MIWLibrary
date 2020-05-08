package com.miw.dsdm.miwlibrary.data.storage.db.mappers

import com.miw.dsdm.miwlibrary.data.storage.db.entities.CategoryEntity
import com.miw.dsdm.miwlibrary.model.Category

object CategoryDataMapper {

    /**
     * Function to convert each category in the list that is passed by parameter into a domain object
     */
    fun convertToDomain(categories: List<CategoryEntity>) = categories.map { convertCategoryToDomain(it) }

    /**
     * Function to convert the category passed by parameter to a domain object (category)
     */
    private fun convertCategoryToDomain(category: CategoryEntity) = Category(
        category.id, category.name, category.nicename
    )

    /**
     * Function to convert the categories of the list passed by parameter into objects that can be stored in the database
     */
    fun convertFromDomain(categories: List<Category>) = categories.map { convertCategoryFromDomain(it) }

    /**
     * Function to convert the category passed by parameter to a database object (categoryEntity)
     */
    private fun convertCategoryFromDomain(category: Category) = CategoryEntity(
        category.name, category.nicename
    )


}