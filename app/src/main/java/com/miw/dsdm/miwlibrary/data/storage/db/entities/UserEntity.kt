package com.miw.dsdm.miwlibrary.data.storage.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

object UserTable {
    const val TABLE_NAME = "User"
    const val NAME = "name"
    const val SURNAME = "surname"
    const val EMAIL = "email"
    const val PASSWORD = "password"
}

@Entity(tableName = UserTable.TABLE_NAME)
data class UserEntity(
    @ColumnInfo(name = UserTable.NAME) @NotNull val name: String,
    @ColumnInfo(name = UserTable.SURNAME) @NotNull val surname: String,
    @PrimaryKey @ColumnInfo(name = UserTable.EMAIL) @NotNull val email: String,
    @ColumnInfo(name = UserTable.PASSWORD) @NotNull val password: String
)