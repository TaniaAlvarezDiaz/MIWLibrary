package com.miw.dsdm.miwlibrary.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.User
import com.miw.dsdm.miwlibrary.utils.Validations
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.alertdialog.appcompat.*


class LoginActivity : AppCompatActivity() {

    private lateinit var loadingDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initialize()
    }

    /**
     * Function to initialize components
     */
    private fun initialize() {
        loadingDialog = SpotsDialog.Builder().setContext(this).setTheme(R.style.dialog).setCancelable(false).build()
        login_btn_login.setOnClickListener { onSubmit() }
        login_register.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
    }

    /**
     * Function to login user
     */
    private fun onSubmit() {
        val login = login_username_value.text.toString()
        val password = login_password_value.text.toString()

        validate(login, password)
    }

    /**
     * Function to show or not show the password
     */
    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            if(checked)
                login_password_value.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
            else
                login_password_value.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    /**
     * Function to validate user data
     */
    private fun validate(email: String, password: String) {
        var valid = true

        //Campos obligatorios sin rellenar
        if (email.isEmpty()) {
            login_username_value.error = getString(R.string.error_empty_field)
            valid = false
        } else if (!Validations.validateEmail(email)) {
            login_username_value.error = getString(R.string.error_email_invalid)
            valid = false
        } else {
            login_username_value.error = null
        }

        if (password.isEmpty()) {
            login_password_value.error = getString(R.string.error_empty_field)
            valid = false
        } else {
            login_password_value.error = null
        }

        if (valid) {
            loginUser(email, password)
        }

    }

    /**
     * It checks whether a user exists.
     * If the credentials are correct, the main page is displayed, but the error is displayed
     */
    private fun loginUser(email: String, password: String) {
        loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = User.requestUserByEmailAndPassword(email, password)
            if (result != null) {
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    Settings(this@LoginActivity).userLoggedIn = email
                    startActivity(Intent(this@LoginActivity, NavigationActivity::class.java))
                }
            } else {
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    showDialog(
                        getString(R.string.login_error_alert_title),
                        getString(R.string.login_error_alert_message)
                    )
                }
            }
        }
    }

    /**
     * Function to show an alert dialog
     */
    private fun showDialog(t: String, m: String) {
        alertDialog {
            title = t
            message = m
            okButton {}
        }.onShow {
            setCancelable(false)
        }.show()
    }
}
