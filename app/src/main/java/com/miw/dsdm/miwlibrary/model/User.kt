package com.miw.dsdm.miwlibrary.model

import android.os.Parcel
import android.os.Parcelable
import com.miw.dsdm.miwlibrary.data.datasources.UserProvider

data class User(
    val name: String, val surname: String,
    val email: String, var password: String,
    val repeatPassword: String
) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(repeatPassword)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            val name = parcel.readString() ?: ""
            val surname = parcel.readString() ?: ""
            val email = parcel.readString() ?: ""
            val password = parcel.readString() ?: ""
            val repeatPassword = parcel.readString() ?: ""
            return User(name, surname, email, password, repeatPassword)
        }

        override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)

        fun requestUserByEmail(email: String): User? = UserProvider.findByEmail(email)

        fun requestUserByEmailAndPassword(email: String, password: String): User? = UserProvider.findByEmailAndPassword(email, password)

        fun requestSaveUser(user: User): Boolean = UserProvider.saveUser(user)

        fun requestUpdateUser(user: User): Boolean = UserProvider.updateUser(user)
    }
}
