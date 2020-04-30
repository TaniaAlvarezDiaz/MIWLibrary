package com.miw.dsdm.miwlibrary

import android.app.Application

class LibraryApp : Application() {
    companion object {
        lateinit var instance: LibraryApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}