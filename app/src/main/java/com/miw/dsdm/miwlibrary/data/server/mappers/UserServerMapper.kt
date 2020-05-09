package com.miw.dsdm.miwlibrary.data.server.mappers

import com.miw.dsdm.miwlibrary.data.server.UserResponse
import com.miw.dsdm.miwlibrary.model.User

object UserServerMapper {

    /**
     * Function to convert each user in the list that is passed by parameter into a domain object
     */
    fun convertToDomain(users: List<UserResponse>) = users.map { convertUserToDomain(it) }

    /**
     * Function to convert the category passed by parameter to a domain object (category)
     */
    private fun convertUserToDomain(user: UserResponse) = User(
        user.name, user.surname, user.email, user.password, user.password
    )
}