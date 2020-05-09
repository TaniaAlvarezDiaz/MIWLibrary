package com.miw.dsdm.miwlibrary.data.storage.db.dao

import androidx.room.*
import com.miw.dsdm.miwlibrary.data.storage.db.entities.UserEntity
import com.miw.dsdm.miwlibrary.data.storage.db.entities.UserTable

@Dao
interface UserDao {
    @Insert
    fun insert(user: UserEntity): Long

    @Update
    fun update(vararg user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("DELETE FROM ${UserTable.TABLE_NAME}")
    fun clear()

    @Query("SELECT * FROM ${UserTable.TABLE_NAME}")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.EMAIL} LIKE :email LIMIT 1")
    fun findByEmail(email: String): UserEntity

    @Query("SELECT * FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.EMAIL} LIKE :email AND ${UserTable.PASSWORD} LIKE :password")
    fun findByEmailAndPassword(email: String, password: String): UserEntity
}