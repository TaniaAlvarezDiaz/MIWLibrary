package com.miw.dsdm.miwlibrary.ui.activities

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.Book
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_book.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.message
import splitties.alertdialog.appcompat.okButton
import splitties.alertdialog.appcompat.onShow

class BookActivity : AppCompatActivity() {

    companion object {
        const val BOOK = "BookActivity:book"
    }

    private lateinit var loadingDialog: AlertDialog
    lateinit var book: Book
    private var userEmail: String = ""
    private var favorite: Boolean = false

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
        //User logged
        userEmail = Settings(this).userLoggedIn.toString()

        //Loading
        loadingDialog = SpotsDialog.Builder().setContext(this).setTheme(R.style.dialog).setCancelable(false).build()

        //Toolbar
        setSupportActionBar(book_toolbar)

        //Get book from Intent
        book = intent.getParcelableExtra(BOOK)

        if (book != null) fillFields()

        //Button
        book_btn_add_favorites.setOnClickListener {
            if (favorite) deleteFavoriteBook(book)
            else addFavoriteBook(book)
        }

        //Check if the book is favorite or not
        isFavorite()
    }

    /**
     * Function to fill fields with the information from the book
     */
    private fun fillFields() {
        with(book) {
            //Image
            Glide.with(this@BookActivity).load(imagePath).error(R.drawable.book_cover_not_available).into(book_image)
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

    /**
     * Function to check if the book is favorite or not
     */
    private fun isFavorite() {
        loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = Book.isFavoriteBook(userEmail, book)
            if (result != null) {
                favorite = result
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    changeButtonName()
                }
            } else {
                withContext(Dispatchers.Main) {
                    loadingDialog.dismiss()
                    changeButtonName()
                }
            }
        }
    }

    /**
     * Function to change button name
     */
    private fun changeButtonName() {
        book_btn_add_favorites.text = if (favorite) getString(R.string.book_btn_delete_favorites) else getString(R.string.book_btn_add_favorites)
    }

    /**
     * Delete favorite book
     */
    fun deleteFavoriteBook(book: Book) {
        loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            Book.deleteFavoriteBook(userEmail, book)
            withContext(Dispatchers.Main) {
                favorite = false
                loadingDialog.dismiss()
                showDialog(getString(R.string.book_delete_favorite_alert_message))
            }
        }
    }

    /**
     * Add favorite book
     */
    fun addFavoriteBook(book: Book) {
        loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            Book.saveFavoriteBook(userEmail, book)
            withContext(Dispatchers.Main) {
                favorite = true
                loadingDialog.dismiss()
                showDialog(getString(R.string.book_save_favorite_alert_message))
            }
        }
    }

    /**
     * Function to show an alert dialog
     */
    private fun showDialog(m: String) {
        alertDialog {
            message = m
            okButton { changeButtonName() }
        }.onShow {
            setCancelable(false)
        }.show()
    }

}
