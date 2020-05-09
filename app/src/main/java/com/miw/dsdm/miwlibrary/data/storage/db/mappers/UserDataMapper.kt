package com.miw.dsdm.miwlibrary.data.storage.db.mappers

import com.miw.dsdm.miwlibrary.data.storage.db.entities.UserEntity
import com.miw.dsdm.miwlibrary.model.User

object UserDataMapper {

    /**
     * Function to convert a user that is passed by parameter into a domain object
     */
    fun convertToDomain(user: UserEntity?) = convertUserToDomain(user)

    /**
     * Function to convert the user passed by parameter to a domain object (user)
     */
    private fun convertUserToDomain(user: UserEntity?): User? {
        if (user != null)
            return User(user.name, user.surname, user.email, user.password, user.password)
        return null
    }

    /**
     * Function to convert a user that is passed by parameter into a domain object
     */
    fun convertFromDomain(user: User) = convertUserFromDomain(user)

    /**
     * Function to convert the user passed by parameter to a database object (userEntity)
     */
    private fun convertUserFromDomain(user: User) = UserEntity(user.name, user.surname, user.email, user.password)

}