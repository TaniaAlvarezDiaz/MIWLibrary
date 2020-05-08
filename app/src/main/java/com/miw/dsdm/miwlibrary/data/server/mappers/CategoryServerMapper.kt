package com.miw.dsdm.miwlibrary.data.server.mappers

import com.miw.dsdm.miwlibrary.data.server.CategoryResponse
import com.miw.dsdm.miwlibrary.model.Category

object CategoryServerMapper {

    /**
     * Function to convert each category in the list that is passed by parameter into a domain object
     */
    fun convertToDomain(categories: List<CategoryResponse>) = categories.map { convertCategoryToDomain(it) }

    /**
     * Function to convert the category passed by parameter to a domain object (category)
     */
    private fun convertCategoryToDomain(category: CategoryResponse) = Category(
        category.id, category.name, category.nicename
    )
}