package com.miw.dsdm.miwlibrary.data.storage.db.dao

import androidx.room.*
import com.miw.dsdm.miwlibrary.data.storage.db.entities.*

@Dao
interface BookCategoryDao {

    @Insert
    fun insert(bookCategory: BookCategoryEntity): Long

    @Update
    fun update(vararg bookCategory: BookCategoryEntity)

    @Delete
    fun delete(bookCategory: BookCategoryEntity)

    @Query("DELETE FROM ${BookCategoryTable.TABLE_NAME}")
    fun clear()

    @Query(
        "SELECT * FROM ${BookTable.TABLE_NAME} " +
                "INNER JOIN ${BookCategoryTable.TABLE_NAME} " +
                "ON ${BookTable.ID} LIKE ${BookCategoryTable.BOOK_ID} " +
                "WHERE ${BookCategoryTable.CATEGORY_ID} LIKE :categoryId"
    )
    fun getBooksByCategory(categoryId: Long): List<BookEntity>

    @Query(
        "SELECT * FROM ${CategoryTable.TABLE_NAME} " +
                "INNER JOIN ${BookCategoryTable.TABLE_NAME} " +
                "ON ${CategoryTable.ID} LIKE ${BookCategoryTable.CATEGORY_ID} " +
                "WHERE ${BookCategoryTable.BOOK_ID} LIKE :bookId"
    )
    fun getCategoriesByBook(bookId: Long): List<CategoryEntity>
}