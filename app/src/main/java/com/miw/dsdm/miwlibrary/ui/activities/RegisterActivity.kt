package com.miw.dsdm.miwlibrary.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.db.repositories.UserRepository
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.User
import com.miw.dsdm.miwlibrary.utils.PASSWORD_MINIMUN_LENGTH
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.alertdialog.appcompat.*

class RegisterActivity : AppCompatActivity() {

    lateinit var loadingDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initialize()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Function to initialize components
     */
    private fun initialize() {
        loadingDialog = SpotsDialog.Builder().setContext(this).setTheme(R.style.dialog).setCancelable(false).build()
        setSupportActionBar(register_toolbar)
        register_btn_register.setOnClickListener { onSubmit() }
    }

    /**
     * Function to register user
     */
    private fun onSubmit() {
        val name = register_name_value.text.toString()
        val surname = register_surname_value.text.toString()
        val email = register_email_value.text.toString()
        val password1 = register_password_value.text.toString()
        val password2 = register_repeat_password_value.text.toString()

        val user = User(name, surname, email, password1, password2)

        validate(user)
    }

    /**
     * Function to validate user data
     */
    private fun validate(user: User){
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

        if(valid){
           registerUser(user)
        }
    }

    /**
     * It checks whether a user exists.
     * If there is an error message is displayed to the user, otherwise the user is logged
     */
    private fun registerUser(user: User){
        loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = User.requestUserByEmail(user.email)
            if (result == null) {
                val reg = User.requestSaveUser(user)
                if(reg){
                    withContext(Dispatchers.Main) {
                        loadingDialog.dismiss()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }
                }
                else{
                    withContext(Dispatchers.Main) {
                        loadingDialog.dismiss()
                        showDialog(getString(R.string.register_user_exist_alert_title),
                            getString(R.string.error_password_length))
                    }
                }
            }
            else{
                withContext(Dispatchers.Main) {
                   loadingDialog.dismiss()
                   showDialog(getString(R.string.register_user_exist_alert_title),
                       getString(R.string.register_user_exist_alert_message))
                }
            }
        }
    }

    private fun showDialog(t: String, m: String){
        alertDialog {
            title = t
            message = m
            okButton {
            }
        }.onShow {
            setCancelable(false)
        }.show()
    }
}