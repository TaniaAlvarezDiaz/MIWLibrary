package com.miw.dsdm.miwlibrary.data.storage.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

object BookTable {
    const val TABLE_NAME = "Book"
    const val ID = "id"
    const val IMAGE = "image"
    const val TITLE = "tile"
    const val AUTHOR = "author"
    const val LANGUAGE = "language"
    const val CONTENT = "content"
    const val SUMMARY = "summary"
    const val PUBLISHER = "publisher"
    const val PUBLICATION_YEAR = "publicationYear"
    const val DETAILS_URL = "detailsUrl"
}

@Entity(tableName = BookTable.TABLE_NAME)
data class BookEntity(
    @ColumnInfo(name = BookTable.IMAGE) @NotNull val imagePath: String,
    @ColumnInfo(name = BookTable.TITLE) @NotNull val title: String,
    @ColumnInfo(name = BookTable.AUTHOR) @NotNull val author: String,
    @ColumnInfo(name = BookTable.LANGUAGE) @NotNull val language: String,
    @ColumnInfo(name = BookTable.CONTENT) @NotNull val content: String,
    @ColumnInfo(name = BookTable.SUMMARY) @NotNull val summary: String,
    @ColumnInfo(name = BookTable.PUBLISHER) @NotNull val publisher: String,
    @ColumnInfo(name = BookTable.PUBLICATION_YEAR) @NotNull val publicationYear: String,
    @ColumnInfo(name = BookTable.DETAILS_URL) @NotNull val detailsUrl: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BookTable.ID)
    var id: Long = 0
}