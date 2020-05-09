package com.miw.dsdm.miwlibrary.data.storage.db.repositories

import com.miw.dsdm.miwlibrary.data.datasources.UserDataSource
import com.miw.dsdm.miwlibrary.data.storage.db.LibraryDatabase
import com.miw.dsdm.miwlibrary.data.storage.db.mappers.UserDataMapper
import com.miw.dsdm.miwlibrary.model.User

class UserRepository : UserDataSource {

    private val userDao = LibraryDatabase.instance.userDao()

    /**
     * Function to get user by email
     */
    fun requestUserByEmail(email: String): User? {
        val userDb = userDao.findByEmail(email)
        return UserDataMapper.convertToDomain(userDb)
    }

    /**
     * Function to get user by email and password
     */
    fun requestUserByEmailAndPassword(email: String, password : String): User ?{
        val userDb = userDao.findByEmailAndPasswrod(email, password)
        return UserDataMapper.convertToDomain(userDb)
    }

    /**
     * Function to save a new user
     */
    fun requestSaveNewUser(user: User) : Boolean{
        val userDb = UserDataMapper.convertFromDomain(user)
        userDao.insert(userDb)
        return true
    }

    /**
     * Function to update a user
     */
    fun requestUpdateUser(user: User): Boolean{
        val userDb = UserDataMapper.convertFromDomain(user)
        userDao.update(userDb)
        return true
    }

}