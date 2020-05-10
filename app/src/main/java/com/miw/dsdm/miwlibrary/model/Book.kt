package com.miw.dsdm.miwlibrary.model

import android.os.Parcel
import android.os.Parcelable
import com.miw.dsdm.miwlibrary.data.datasources.BookProvider
import com.miw.dsdm.miwlibrary.data.storage.local.Settings

class Book(
    val id: Long,
    val imagePath: String?,
    val title: String?,
    val author: String?,
    val language: String?,
    val content: String?,
    val summary: String?,
    val publisher: String?,
    val publicationYear: String?,
    val detailsUrl: String?,
    var categories: List<Category>
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(imagePath)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(language)
        parcel.writeString(content)
        parcel.writeString(summary)
        parcel.writeString(publisher)
        parcel.writeString(publicationYear)
        parcel.writeString(detailsUrl)
        parcel.writeTypedList(categories)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Book> {

        override fun createFromParcel(parcel: Parcel): Book {
            val id = parcel.readLong()
            val imagePath = parcel.readString() ?: ""
            val title = parcel.readString() ?: ""
            val author = parcel.readString() ?: ""
            val language = parcel.readString() ?: ""
            val content = parcel.readString() ?: ""
            val summary = parcel.readString() ?: ""
            val publisher = parcel.readString() ?: ""
            val publicationYear = parcel.readString() ?: ""
            val detailsUrl = parcel.readString() ?: ""
            val categories = emptyList<Category>()
            parcel.readList(categories, Category.javaClass.classLoader)

            return Book(id, imagePath, title, author, language, content, summary, publisher, publicationYear, detailsUrl, categories)
        }

        override fun newArray(size: Int): Array<Book?> = arrayOfNulls(size)

        fun requestAllBooks() : List<Book> = BookProvider.requestAllBooks()

        fun requestAllFavoritesBooks(userEmail: String) : List<Book> = BookProvider.requestFavoritesBooksByUser(userEmail)

        fun saveFavoriteBook(userEmail: String, book: Book) = BookProvider.saveFavoriteBook(userEmail, book)

        fun deleteFavoriteBook(userEmail: String, book: Book) = BookProvider.deleteFavoriteBook(userEmail, book)

        fun isFavoriteBook(userEmail: String, book: Book) : Boolean = BookProvider.isFavoriteBook(userEmail, book)
    }

}
