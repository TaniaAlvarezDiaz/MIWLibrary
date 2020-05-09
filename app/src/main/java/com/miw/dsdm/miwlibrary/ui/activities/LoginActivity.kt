package com.miw.dsdm.miwlibrary.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.User
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.alertdialog.appcompat.*

class LoginActivity : AppCompatActivity() {

    lateinit var loadingDialog : AlertDialog

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
     * Function to validate user data
     */
    private fun validate(email: String, password: String){
        var valid = true

        //Campos obligatorios sin rellenar
        if (email.isEmpty()) {
            login_username_value.error = getString(R.string.error_empty_field)
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

        if(valid){
            loginUser(email, password)
        }

    }

    /**
     * It checks whether a user exists.
     * If the credentials are correct, the main page is displayed, but the error is displayed
     */
    private fun loginUser(email: String, password: String){
        loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = User.requestUserByEmailAndPassword(email, password)
            if (result != null) {
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    Settings(this@LoginActivity).userLoggedIn = email
                    startActivity(Intent(this@LoginActivity, NavigationActivity::class.java))
                }
            }
            else{
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    showDialog("",
                        getString(R.string.login_error_alert_message))
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
