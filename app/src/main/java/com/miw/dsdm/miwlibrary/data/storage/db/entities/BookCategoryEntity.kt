package com.miw.dsdm.miwlibrary.data.storage.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import org.jetbrains.annotations.NotNull

object BookCategoryTable {
    const val TABLE_NAME = "BookCategory"
    const val BOOK_ID = "bookId"
    const val CATEGORY_ID = "categoryId"
}

@Entity(
    tableName = BookCategoryTable.TABLE_NAME,
    primaryKeys = [BookCategoryTable.BOOK_ID, BookCategoryTable.CATEGORY_ID],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = BookEntity::class,
            parentColumns = [BookTable.ID],
            childColumns = [BookCategoryTable.BOOK_ID],
            onDelete = androidx.room.ForeignKey.CASCADE),
        androidx.room.ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = [CategoryTable.ID],
            childColumns = [BookCategoryTable.CATEGORY_ID],
            onDelete = androidx.room.ForeignKey.CASCADE)
    ]
)
data class BookCategoryEntity(
    @ColumnInfo(name = BookCategoryTable.BOOK_ID) @NotNull val bookId: Long,
    @ColumnInfo(name = BookCategoryTable.CATEGORY_ID) @NotNull val categoryId: Long
)