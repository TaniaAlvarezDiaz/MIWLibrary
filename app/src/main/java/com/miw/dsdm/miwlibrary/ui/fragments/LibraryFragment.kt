package com.miw.dsdm.miwlibrary.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.Book
import com.miw.dsdm.miwlibrary.ui.activities.BookActivity
import com.miw.dsdm.miwlibrary.ui.activities.NavigationActivity
import com.miw.dsdm.miwlibrary.ui.adapters.LibraryAdapter
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LibraryFragment : Fragment() {
    companion object {
        fun newInstance(): LibraryFragment = LibraryFragment()
    }

    private lateinit var libraryAdapter: LibraryAdapter

    private var books: MutableList<Book> = mutableListOf()
    private var searchText: String = ""
    private var userEmail: String = ""
    private var connectionInternet : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_library, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        //User logged
        userEmail = activity?.let { Settings(it).userLoggedIn.toString() }.toString()

        //SwipeRefreshLayout
        library_swipe_refresh_layout.setOnRefreshListener {
            library_swipe_refresh_layout.isRefreshing = true
            checkInternetConnection()
        }

        //SearchView
        initializeSearchView()

        //Recycler adapter
        libraryAdapter = LibraryAdapter(books) {
            goToBookInformation(it)
        }

        //Recycler
        library_recycler_view.layoutManager = LinearLayoutManager(activity)
        library_recycler_view.adapter = libraryAdapter

        //Get books
        checkInternetConnection()
    }

    /**
     * Function to check if there is internet connection
     */
    private fun checkInternetConnection() {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork
        connectionInternet = if (activeNetwork != null) true else false
        getBooks()
    }

    /**
     * Function to initialize searchView
     */
    private fun initializeSearchView() {
        //Configure the queryTextListener
        library_search_view.setOnQueryTextListener(object : OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchText = newText.toString()
                libraryAdapter.filter.filter(newText)
                return false
            }
        })
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
     * Function to update library adapter
     */
    private fun updateLibraryAdapter() {
        //Update books
        libraryAdapter = LibraryAdapter(books) {
            goToBookInformation(it)
        }
        library_recycler_view.adapter = libraryAdapter

        showNoResultsMessage()
    }

    /**
     * Function to get books
     */
    private fun getBooks() {
        (activity as NavigationActivity).loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = Book.requestAllBooks(connectionInternet)
            if (!result.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    (activity as NavigationActivity).loadingDialog.dismiss()
                    library_swipe_refresh_layout.isRefreshing = false
                    books.addAll(result)
                    updateLibraryAdapter()
                }
            } else {
                withContext(Dispatchers.Main) {
                    (activity as NavigationActivity).loadingDialog.dismiss()
                    library_swipe_refresh_layout.isRefreshing = false
                    showNoResultsMessage()
                }
            }
        }
    }

    /**
     * Function to show no results message
     */
    private fun showNoResultsMessage() {
        //Show/hide text "No results"
        library_no_results.visibility = if (books.isEmpty()) View.VISIBLE else View.GONE
        library_recycler_view.visibility = if (books.isEmpty()) View.GONE else View.VISIBLE
    }
}
