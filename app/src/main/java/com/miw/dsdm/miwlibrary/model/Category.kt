package com.miw.dsdm.miwlibrary.model

import android.os.Parcel
import android.os.Parcelable
import com.miw.dsdm.miwlibrary.data.datasources.CategoryProvider

class Category(val id: Long, val name: String, val nicename: String) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(nicename)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            val id = parcel.readLong()
            val name = parcel.readString() ?: ""
            val nicename = parcel.readString() ?: ""
            return Category(id, name, nicename)
        }

        override fun newArray(size: Int): Array<Category?> = arrayOfNulls(size)

        fun requestAllCategories() = CategoryProvider.requestAllCategories()
    }
}