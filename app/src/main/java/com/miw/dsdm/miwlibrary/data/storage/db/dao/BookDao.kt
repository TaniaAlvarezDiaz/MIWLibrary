package com.miw.dsdm.miwlibrary.data.storage.db.dao

import androidx.room.*
import com.miw.dsdm.miwlibrary.data.storage.db.entities.BookEntity
import com.miw.dsdm.miwlibrary.data.storage.db.entities.BookTable

@Dao
interface BookDao {

    @Insert
    fun insert(book: BookEntity): Long

    @Update
    fun update(vararg book: BookEntity)

    @Delete
    fun delete(book: BookEntity)

    @Query("DELETE FROM ${BookTable.TABLE_NAME}")
    fun clear()

    @Query("SELECT * FROM ${BookTable.TABLE_NAME}")
    fun getAll(): List<BookEntity>

}