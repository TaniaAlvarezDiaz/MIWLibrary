package com.miw.dsdm.miwlibrary.utils

import java.util.regex.Pattern

object Validations {
    /**
     * Function to check if the format of the email that is passed by parameter is valid
     */
    fun validateEmail(email: String): Boolean {
        // Patrón para validar el email
        val pattern: Pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        return pattern.matcher(email).find()
    }
}