package com.miw.dsdm.miwlibrary.data.storage.local

import android.content.Context

class SharedPreferenceStorage(val context: Context) : LocalStorage {

    companion object {
        const val PREF_FILE_NAME = "MIWLibrary"
        const val USER_LOGGED_IN = "userLoggedIn"
    }

    val sharedPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)

    override fun clearAll() {
        with(sharedPref.edit()) {
            clear()
            commit()
        }
    }

    override fun saveUserLoggedIn(id: Long) {
        with(sharedPref.edit()) {
            putLong(USER_LOGGED_IN, id)
            commit()
        }
    }

    override fun getUserLoggedIn(): Long {
        return sharedPref.getLong(USER_LOGGED_IN, 0)
    }

    override fun removeUserLoggedInt() {
        with(sharedPref.edit()) {
            remove(USER_LOGGED_IN)
            commit()
        }
    }
}