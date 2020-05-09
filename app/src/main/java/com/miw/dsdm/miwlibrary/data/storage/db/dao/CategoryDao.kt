package com.miw.dsdm.miwlibrary.data.storage.db.dao

import androidx.room.*
import com.miw.dsdm.miwlibrary.data.storage.db.entities.CategoryEntity
import com.miw.dsdm.miwlibrary.data.storage.db.entities.CategoryTable

@Dao
interface CategoryDao {

    @Insert
    fun insert(category: CategoryEntity): Long

    @Update
    fun update(vararg category: CategoryEntity)

    @Delete
    fun delete(category: CategoryEntity)

    @Query("DELETE FROM ${CategoryTable.TABLE_NAME}")
    fun clear()

    @Query("SELECT * FROM ${CategoryTable.TABLE_NAME}")
    fun getAll(): List<CategoryEntity>
}