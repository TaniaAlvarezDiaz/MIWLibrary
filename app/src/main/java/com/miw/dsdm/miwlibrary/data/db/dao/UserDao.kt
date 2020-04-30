package com.miw.dsdm.miwlibrary.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.miw.dsdm.miwlibrary.data.db.entities.UserEntity
import com.miw.dsdm.miwlibrary.data.db.entities.UserTable

interface UserDao {
    @Insert
    fun insert(user: UserEntity): Long

    @Update
    fun update(vararg user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT * FROM ${UserTable.TABLE_NAME}")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.ID} IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserEntity>

    @Query("SELECT * FROM ${UserTable.TABLE_NAME} WHERE ${UserTable.EMAIL} LIKE :email")
    fun findByEmail(email: String): UserEntity
}