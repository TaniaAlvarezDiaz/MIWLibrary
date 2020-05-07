package com.miw.dsdm.miwlibrary.model

import android.os.Parcel
import android.os.Parcelable

class Book(
    val imagePath: String,
    val title: String,
    val author: String,
    val language: String,
    val content: String,
    val summary: String,
    val publisher: String,
    val publicationYear: String,
    val detailsUrl: String,
    val categories: List<Category>
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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

            return Book(imagePath, title, author, language, content, summary, publisher, publicationYear, detailsUrl, categories)
        }

        override fun newArray(size: Int): Array<Book?> = arrayOfNulls(size)
    }

}
