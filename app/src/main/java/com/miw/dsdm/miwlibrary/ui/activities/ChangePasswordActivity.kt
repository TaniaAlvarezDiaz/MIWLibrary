package com.miw.dsdm.miwlibrary.ui.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.User
import com.miw.dsdm.miwlibrary.utils.PASSWORD_MINIMUN_LENGTH
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.alertdialog.appcompat.*

class ChangePasswordActivity : AppCompatActivity() {

    lateinit var loadingDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
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
        setSupportActionBar(change_password_toolbar)
        change_password_btn_password.setOnClickListener { onSubmit() }
    }

    /**
     * Function to login user
     */
    private fun onSubmit() {
        val oldPassword = change_password_old_value.text.toString()
        val newPassword = change_password_new_value.text.toString()
        val repeatNewPassword = change_password_new_repeat_value.text.toString()

        validate(oldPassword, newPassword, repeatNewPassword)
    }

    /**
     * Function to validate user data
     */
    private fun validate(oldPassword: String, newPassword: String, repeatNewPassword: String){
        var valid = true

        if (oldPassword.isEmpty()) {
            change_password_old_value.error = getString(R.string.error_empty_field)
            valid = false
        } else {
            change_password_old_value.error = null
        }

        if (newPassword.isEmpty()) {
            change_password_new_value.error = getString(R.string.error_empty_field)
            valid = false
        } else {
            if (change_password_new_value.length() < PASSWORD_MINIMUN_LENGTH) {
                change_password_new_value.error = getString(R.string.error_password_length)
                valid = false
            } else {
                change_password_new_value.error = null
            }
        }

        if (repeatNewPassword.isEmpty()) {
            change_password_new_repeat_value.error = getString(R.string.error_empty_field)
            valid = false
        } else {
            change_password_new_repeat_value.error = null
        }

        if(!newPassword.equals(repeatNewPassword)){
            change_password_new_repeat_value.error = getString(R.string.error_passwords_different)
            valid = false
        } else {
            change_password_new_repeat_value.error = null
        }

        if(valid){
            changePassword(newPassword, oldPassword)
        }

    }

    /**
     * Change the user's password
     */
    private fun changePassword(newPassword: String, oldPassword: String){
        loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = Settings(this@ChangePasswordActivity).userLoggedIn?.let { User.requestUserByEmailAndPassword(it, oldPassword) }
            if (result != null) {
                result.password = newPassword
                val update =  User.requestUpdateUser(result)
                if(update){
                    withContext(Dispatchers.Main) {
                        loadingDialog.dismiss()
                        showDialog("",
                            getString(R.string.change_password_alert_message))
                    }
                }
                else{
                    withContext(Dispatchers.Main) {
                        loadingDialog.dismiss()
                        showDialog(getString(R.string.change_password_error_alert_title),
                            getString(R.string.error_alert_message))
                    }
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
                onBackPressed()
            }
        }.onShow {
            setCancelable(false)
        }.show()
    }
}
