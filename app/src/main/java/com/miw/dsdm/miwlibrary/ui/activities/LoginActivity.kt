package com.miw.dsdm.miwlibrary.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miw.dsdm.miwlibrary.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        initialize()
    }

    /**
     * Function to initialize components
     */
    private fun initialize() {
        login_btn_login.setOnClickListener { startActivity(Intent(this, NavigationActivity::class.java)) }
        login_register.setOnClickListener { startActivity(Intent(this, RegisterActivity::class.java)) }
    }
}
