package com.miw.dsdm.miwlibrary.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miw.dsdm.miwlibrary.R
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Function to initialize components
     */
    private fun initialize() {
        setSupportActionBar(change_password_toolbar)
        change_password_btn_password.setOnClickListener { onSubmit() }
    }

    private fun onSubmit() {

    }
}
