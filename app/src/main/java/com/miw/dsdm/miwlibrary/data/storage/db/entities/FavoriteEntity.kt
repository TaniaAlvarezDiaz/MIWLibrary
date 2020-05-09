package com.miw.dsdm.miwlibrary.data.storage.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import org.jetbrains.annotations.NotNull

object FavoriteTable {
    const val TABLE_NAME = "Favorite"
    const val USER_EMAIL = "userEmail"
    const val BOOK_ID = "bookId"
}

@Entity(
    tableName = FavoriteTable.TABLE_NAME,
    primaryKeys = [FavoriteTable.USER_EMAIL, FavoriteTable.BOOK_ID],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserTable.EMAIL],
            childColumns = [FavoriteTable.USER_EMAIL],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = [BookTable.ID],
            childColumns = [FavoriteTable.BOOK_ID],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteEntity(
    @ColumnInfo(name = FavoriteTable.USER_EMAIL) @NotNull val userEmail: String,
    @ColumnInfo(name = FavoriteTable.BOOK_ID) @NotNull val bookId: Long
)