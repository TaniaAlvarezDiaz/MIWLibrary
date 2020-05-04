package com.miw.dsdm.miwlibrary.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.model.User
import com.miw.dsdm.miwlibrary.utils.PASSWORD_MINIMUN_LENGTH
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(register_toolbar)
        register_btn_register.setOnClickListener { onSubmit() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun onSubmit() {
        val name = register_name_value.text.toString()
        val surname = register_surname_value.text.toString()
        val email = register_email_value.text.toString()
        val password1 = register_password_value.text.toString()
        val password2 = register_repeat_password_value.text.toString()

        val user = User(name, surname, email, password1, password2)

        if (validate(user)) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validate(user: User): Boolean {
        var valid = true

        //Campos obligatorios sin rellenar
        if (user.name.isEmpty()) {
            register_name_value.error = getString(R.string.error_empty_field)
            valid = false
        } else {
            register_name_value.error = null
        }

        if (user.surname.isEmpty()) {
            register_surname_value.error = getString(R.string.error_empty_field)
            valid = false
        } else {
            register_surname_value.error = null
        }

        if (user.email.isEmpty()) {
            register_email_value.error = getString(R.string.error_empty_field)
            valid = false
        } else {
            register_email_value.error = null
        }

        if (user.password.isEmpty()) {
            register_password_value.error = getString(R.string.error_empty_field)
            valid = false
        } else {
            if (user.password.length < PASSWORD_MINIMUN_LENGTH) {
                register_password_value.error = getString(R.string.error_password_length)
                valid = false
            } else {
                register_password_value.error = null
            }
        }

        if (!user.password.equals(user.repeatPassword)) {
            register_repeat_password_value.error = getString(R.string.error_passwords_different)
            valid = false
        } else {
            register_repeat_password_value.error = null
        }

        //Llamada a bbdd para buscar el email

        return valid
    }
}