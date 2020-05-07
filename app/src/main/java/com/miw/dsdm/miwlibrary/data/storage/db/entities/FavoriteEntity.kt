package com.miw.dsdm.miwlibrary.data.storage.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import org.jetbrains.annotations.NotNull

object FavoriteTable {
    const val TABLE_NAME = "Favorite"
    const val USER_ID = "userId"
    const val BOOK_ID = "bookId"
}

@Entity(
    tableName = FavoriteTable.TABLE_NAME,
    primaryKeys = [FavoriteTable.USER_ID, FavoriteTable.BOOK_ID],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserTable.ID],
            childColumns = [FavoriteTable.USER_ID],
            onDelete = androidx.room.ForeignKey.CASCADE),
        androidx.room.ForeignKey(
            entity = BookEntity::class,
            parentColumns = [BookTable.ID],
            childColumns = [FavoriteTable.BOOK_ID],
            onDelete = androidx.room.ForeignKey.CASCADE)
    ]
)
data class FavoriteEntity(
    @ColumnInfo(name = FavoriteTable.USER_ID) @NotNull val userId: Long,
    @ColumnInfo(name = FavoriteTable.BOOK_ID) @NotNull val bookId: Long
)