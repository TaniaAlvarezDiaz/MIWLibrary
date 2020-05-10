package com.miw.dsdm.miwlibrary.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.Book
import com.miw.dsdm.miwlibrary.ui.activities.BookActivity
import com.miw.dsdm.miwlibrary.ui.activities.NavigationActivity
import com.miw.dsdm.miwlibrary.ui.adapters.FavoritesAdapter
import com.miw.dsdm.miwlibrary.ui.adapters.LibraryAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesFragment : Fragment(), FavoritesAdapter.IFavoritesAdapter {
    companion object {
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    private lateinit var favoritesAdapter: FavoritesAdapter
    private var userEmail: String = ""
    private var favoritesBooks: MutableList<Book> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_favorites, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
    }

    override fun onResume() {
        super.onResume()
        //Remove all favorites books
        favoritesBooks.clear()
        //Get favorites books
        getFavoritesBooks()
    }

    private fun initialize() {
        //User logged
        userEmail = activity?.let { Settings(it).userLoggedIn.toString() }.toString()

        //Recycler adapter
        favoritesAdapter = FavoritesAdapter(this, favoritesBooks) {
            goToBookInformation(it)
        }

        //Recycler
        favorites_recycler_view.layoutManager = LinearLayoutManager(activity)
        favorites_recycler_view.adapter = favoritesAdapter
    }

    /**
     * Function to update favorites adapter
     */
    private fun updateFavoritesAdapter() {
        //Update books
        favoritesAdapter = FavoritesAdapter(this, favoritesBooks) {
            goToBookInformation(it)
        }
        favorites_recycler_view.adapter = favoritesAdapter

        showNoResultsMessage()
    }

    /**
     * Function to show no results message
     */
    private fun showNoResultsMessage() {
        //Show/hide text "No results"
        favorites_no_results.visibility = if(favoritesBooks.isEmpty()) View.VISIBLE else View.GONE
    }

    /**
     * Function to go to the screen that shows the information of the book that is passed by parameter
     */
    private fun goToBookInformation(book: Book) {
        val intent = Intent(activity, BookActivity::class.java).apply {
            putExtra(BookActivity.BOOK, book)
        }
        startActivity(intent)
    }

    /**
     * Function to get favorites books
     */
    private fun getFavoritesBooks() {
        (activity as NavigationActivity).loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = activity?.let { Settings(it).userLoggedIn }?.let { Book.requestAllFavoritesBooks(it) }
            if (!result.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    favoritesBooks.addAll(result)
                    (activity as NavigationActivity).loadingDialog.dismiss()
                    updateFavoritesAdapter()
                }
            } else {
                withContext(Dispatchers.Main) {
                    (activity as NavigationActivity).loadingDialog.dismiss()
                    showNoResultsMessage()
                }
            }
        }
    }

    /**
     * Delete favorite book
     */
    override fun deleteFavoriteBook(book: Book) {
        (activity as NavigationActivity).loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            Book.deleteFavoriteBook(userEmail, book)
            withContext(Dispatchers.Main) {
                favoritesBooks.remove(book)
                (activity as NavigationActivity).loadingDialog.dismiss()
                updateFavoritesAdapter()
            }
        }
    }
}
