package com.miw.dsdm.miwlibrary.model

import android.os.Parcel
import android.os.Parcelable

class Book(val title: String?, val author: String?, val description: String?,
           val favorite: Boolean) : Parcelable {

    constructor(parcel: Parcel) : this(
        title = parcel.readString(),
        author = parcel.readString(),
        description = parcel.readString(),
        favorite = parcel.readInt() != 0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(description)
        parcel.writeInt(if (favorite) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Book> {

        override fun createFromParcel(parcel: Parcel): Book = Book(parcel)

        override fun newArray(size: Int): Array<Book?> = arrayOfNulls(size)
    }

}
