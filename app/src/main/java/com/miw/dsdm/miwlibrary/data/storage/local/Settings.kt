package com.miw.dsdm.miwlibrary.data.storage.local

import android.content.Context
import android.content.SharedPreferences

class Settings(context: Context) {

    companion object {
        const val PREFS_NAME = "MIWLibrary"
        const val USER_LOGGED_IN = "userLoggedIn"
        const val USER_LOGGED_IN_DEFAULT = ""
    }

    private val preferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var userLoggedIn: String?
        get() = preferences.getString(USER_LOGGED_IN, USER_LOGGED_IN_DEFAULT)
        set(value) {
            with(preferences.edit()) {
                putString(USER_LOGGED_IN, value)
                apply()
            }
        }

    fun clearAll() = preferences.edit().clear().apply()
}