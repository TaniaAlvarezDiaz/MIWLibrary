package com.miw.dsdm.miwlibrary.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
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
            //Title
            showHideComponents(book_title, book_title_value, title)
            //Author
            showHideComponents(book_author, book_author_value, author)
            //Publisher
            showHideComponents(book_publisher, book_publisher_value, publisher)
            //Publication year
            showHideComponents(book_publication_year, book_publication_year_value, publicationYear)
            //Language
            showHideComponents(book_language, book_language_value, language)
            //Summary
            showHideComponents(book_summary, book_summary_value, summary)
            //Content
            showHideComponents(book_content, book_content_value, content)
            //Categories
            var list = ""
            categories.forEachIndexed { index, category ->
                list += when (index) {
                    0 -> "$category.nicename"
                    else -> ", $category.nicename"
                }
            }
            showHideComponents(book_categories, book_categories_value, list)
            //Details url
            book_more_information.visibility = if (detailsUrl.isNullOrEmpty()) View.GONE else View.VISIBLE
            book_more_information.setOnClickListener { goToUrl() }
        }
    }

    /**
     * Function to hide components if value that is passed by parameter is empty, otherwise the value is assigned
     */
    private fun showHideComponents(componentLabel: TextView, componentValue: TextView, value: String?) {
        componentLabel.visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
        componentValue.visibility = if (value.isNullOrEmpty()) View.GONE else View.VISIBLE
        if (!value.isNullOrEmpty()) componentValue.text = value
    }

    /**
     * Function to go to the book link
     */
    private fun goToUrl() {
        val webpage = Uri.parse(if (book.detailsUrl?.substring(0..3) == "http") book.detailsUrl else "http://$this")
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) startActivity(intent)
    }

}
