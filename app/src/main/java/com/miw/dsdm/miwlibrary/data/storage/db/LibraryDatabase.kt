package com.miw.dsdm.miwlibrary.data.storage.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miw.dsdm.miwlibrary.LibraryApp
import com.miw.dsdm.miwlibrary.data.storage.db.dao.*
import com.miw.dsdm.miwlibrary.data.storage.db.entities.*

@Database(entities = [BookCategoryEntity::class, BookEntity::class, CategoryEntity::class, FavoriteEntity::class, UserEntity::class], version = 1)
abstract class LibraryDatabase : RoomDatabase() {
    abstract fun bookCategoryDao(): BookCategoryDao
    abstract fun bookDao(): BookDao
    abstract fun categoryDao(): CategoryDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun userDao(): UserDao

    companion object {
        private const val DATABASE_NAME = "database"
        val instance by lazy {
            Room.databaseBuilder(
                LibraryApp.instance,
                LibraryDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}