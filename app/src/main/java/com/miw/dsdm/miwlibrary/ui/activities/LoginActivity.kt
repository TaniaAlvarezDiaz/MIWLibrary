package com.miw.dsdm.miwlibrary.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miw.dsdm.miwlibrary.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        //login_btn_login.setOnClickListener { startActivity(Intent(this, NavigationActivity::class.java)) }
    }
}
