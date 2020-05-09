package com.miw.dsdm.miwlibrary.data.datasources

import com.miw.dsdm.miwlibrary.model.Category

interface CategoryDataSource {

    /**
     * Function to get all book categories
     */
    fun requestAllCategories(): List<Category>

}