package com.miw.dsdm.miwlibrary.data.storage.local

interface LocalStorage {

    fun clearAll()

    //User logged in
    fun saveUserLoggedIn(id: Long)
    fun getUserLoggedIn() : Long
    fun removeUserLoggedInt()
}