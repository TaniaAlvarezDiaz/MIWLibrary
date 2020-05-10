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
                "WHERE ${FavoriteTable.USER_EMAIL} LIKE :userEmail"
    )
    fun getFavoritesBooksByUser(userEmail: String): List<BookEntity>

    @Query("SELECT * FROM ${FavoriteTable.TABLE_NAME} " +
            "WHERE ${FavoriteTable.USER_EMAIL} LIKE :userEmail " +
            "AND ${FavoriteTable.BOOK_ID} LIKE :bookId LIMIT 1"
    )
    fun existsFavoriteBook(userEmail: String, bookId: Long) : FavoriteEntity
}