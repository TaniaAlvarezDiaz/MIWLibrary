package com.miw.dsdm.miwlibrary.data.storage.db.dao

import androidx.room.*
import com.miw.dsdm.miwlibrary.data.storage.db.entities.BookEntity
import com.miw.dsdm.miwlibrary.data.storage.db.entities.BookTable
import com.miw.dsdm.miwlibrary.data.storage.db.entities.FavoriteEntity
import com.miw.dsdm.miwlibrary.data.storage.db.entities.FavoriteTable

@Dao
interface FavoriteDao {

    @Insert
    fun insert(favorite: FavoriteEntity): Long

    @Update
    fun update(vararg favorite: FavoriteEntity)

    @Delete
    fun delete(favorite: FavoriteEntity)

    @Query("DELETE FROM ${FavoriteTable.TABLE_NAME}")
    fun clear()

    @Query(
        "SELECT * FROM ${BookTable.TABLE_NAME} " +
                "INNER JOIN ${FavoriteTable.TABLE_NAME} " +
                "ON ${BookTable.ID} LIKE ${FavoriteTable.BOOK_ID} " +
                "WHERE ${FavoriteTable.USER_ID} LIKE :userId"
    )
    fun getFavoritesBooksByUser(userId: Long): List<BookEntity>

}