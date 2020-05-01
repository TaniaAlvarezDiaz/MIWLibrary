package com.miw.dsdm.miwlibrary.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.miw.dsdm.miwlibrary.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        login_btn_login.setOnClickListener { startActivity(Intent(this, NavigationActivity::class.java)) }
    }
}
