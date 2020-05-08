package com.miw.dsdm.miwlibrary.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id: Long,
    val name: String, val surname: String,
    val email: String, val password: String,
    val repeatPassword: String
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(repeatPassword)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            val id = parcel.readLong()
            val name = parcel.readString() ?: ""
            val surname = parcel.readString() ?: ""
            val email = parcel.readString() ?: ""
            val password = parcel.readString() ?: ""
            val repeatPassword = parcel.readString() ?: ""
            return User(id, name, surname, email, password, repeatPassword)
        }

        override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
    }
}
