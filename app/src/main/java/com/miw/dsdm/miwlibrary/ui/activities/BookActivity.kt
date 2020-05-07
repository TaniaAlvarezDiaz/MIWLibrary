package com.miw.dsdm.miwlibrary.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.model.Book
import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {

    companion object {
        const val BOOK = "BookActivity:book"
    }

    lateinit var book: Book

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

        //Get book from Intent
        book = intent.getParcelableExtra(BOOK)

        if (book != null) fillFields()
    }

    /**
     * Function to fill fields with the information from the book
     */
    private fun fillFields() {
        with(book) {
            book_title_value.text = title
            book_author_value.text = author
            book_publisher_value.text = publisher
            book_publication_year_value.text = publicationYear
            book_language_value.text = language
            book_summary_value.text = summary
            book_content_value.text = content
            //Categories
            var list = ""
            categories.forEachIndexed { index, category ->
                list += when (index) {
                    0 -> "$category.nicename"
                    else -> ", $category.nicename"
                }
            }
            book_categories_value.text = list
            //Details url
            book_more_information.visibility = if (detailsUrl.isNullOrEmpty()) View.GONE else View.VISIBLE
            book_more_information.setOnClickListener { goToUrl() }
        }
    }

    /**
     * Function to go to the book link
     */
    private fun goToUrl() {
        val webpage = Uri.parse(if (book.detailsUrl.substring(0..3) == "http") book.detailsUrl else "http://$this")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) startActivity(intent)
    }

}
