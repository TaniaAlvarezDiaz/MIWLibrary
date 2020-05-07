package com.miw.dsdm.miwlibrary.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miw.dsdm.miwlibrary.R
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

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
        setSupportActionBar(book_toolbar)
    }
}
