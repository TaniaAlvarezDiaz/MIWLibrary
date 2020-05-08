package com.miw.dsdm.miwlibrary.data.storage.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

object CategoryTable {
    const val TABLE_NAME = "Category"
    const val ID = "id"
    const val NAME = "name"
    const val NICENAME = "nicename"
}

@Entity(tableName = CategoryTable.TABLE_NAME)
data class CategoryEntity(
    @ColumnInfo(name = CategoryTable.ID) @PrimaryKey val id: Long,
    @ColumnInfo(name = CategoryTable.NAME) @NotNull val name: String,
    @ColumnInfo(name = CategoryTable.NICENAME) @NotNull val nicename: String
)