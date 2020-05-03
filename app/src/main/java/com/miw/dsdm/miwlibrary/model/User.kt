package com.miw.dsdm.miwlibrary.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val name: String, val surname: String,
    val email: String, val password: String,
    val repeatPassword : String
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(repeatPassword)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            val name = parcel.readString() ?: ""
            val surname = parcel.readString() ?: ""
            val email = parcel.readString() ?: ""
            val password = parcel.readString() ?: ""
            val repeatPassword = parcel.readString() ?: ""
            return User(
                name = name,
                surname = surname,
                email = email,
                password = password,
                repeatPassword = repeatPassword
            )
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }


    override fun describeContents(): Int {
        return 0
    }

}
