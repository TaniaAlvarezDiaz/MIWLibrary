package com.miw.dsdm.miwlibrary.data.datasources

import com.miw.dsdm.miwlibrary.data.storage.db.repositories.UserRepository
import com.miw.dsdm.miwlibrary.model.User

object UserProvider {

    /**
     *  Make a call to the repository to search for a user by Email
     */
    fun findByEmail(email: String): User? {
        return UserRepository().requestUserByEmail(email)
    }

    /**
     * Make a call to the repository to search for a user by Email and Password
     */
    fun findByEmailAndPassword(email: String, password: String): User? {
        return UserRepository().requestUserByEmailAndPassword(email, password)
    }

    /**
     * Make a call to the repository to save a new user
     */
    fun saveUser(user: User): Boolean {
        return UserRepository().requestSaveNewUser(user)
    }

    /**
     * Make a call to the repository to update the user that is passed by parameter
     */
    fun updateUser(user: User): Boolean {
        return UserRepository().requestUpdateUser(user)
    }

}