package com.miw.dsdm.miwlibrary.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.Book
import com.miw.dsdm.miwlibrary.model.Category
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
    private var category = 0L
    private var categories: MutableList<Category> = mutableListOf()
    private var userEmail: String = ""

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

        //SearchView
        initializeSearchView()

        //Recycler adapter
        libraryAdapter = LibraryAdapter(books, category) {
          goToBookInformation(it)
        }

        //Recycler
        library_recycler_view.layoutManager = LinearLayoutManager(activity)
        library_recycler_view.adapter = libraryAdapter

        //Get categories from database or server
        getBooksCategories()
        //Get books
//        getBooks()
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
     * Function to initialize categories spinner
     */
    private fun initializeCategoriesSpinner() {
        var categoriesNames: MutableList<String> = mutableListOf()
        //Add categories from database or server
        categories.sortBy { it.name }
        categoriesNames.addAll(categories.map { it.name })
        //Add category empty
        categoriesNames.add(0, getString(R.string.library_filter_categories))

        val adapter = activity?.applicationContext?.let { ArrayAdapter(it, android.R.layout.simple_expandable_list_item_1, categoriesNames) }

        library_spinner_categories.adapter = adapter

        library_spinner_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    category = categories[position - 1].id
                    updateLibraryAdapter()
                }
                libraryAdapter.filter.filter(searchText)
            }
        }
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
        libraryAdapter = LibraryAdapter(books, category) {
            goToBookInformation(it)
        }
        library_recycler_view.adapter = libraryAdapter
    }

    /**
     * Function to get books categories
     */
    private fun getBooksCategories() {
        (activity as NavigationActivity).loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = Category.requestAllCategories()
            if (!result.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    (activity as NavigationActivity).loadingDialog.dismiss()
                    categories.addAll(result)
                    initializeCategoriesSpinner()
                    getBooks()
                }
            } else {
                withContext(Dispatchers.Main) {
                    (activity as NavigationActivity).loadingDialog.dismiss()
                }
            }
        }
    }

    /**
     * Function to get books
     */
    private fun getBooks() {
        (activity as NavigationActivity).loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = Book.requestAllBooks()
            if (!result.isNullOrEmpty()) {
                withContext(Dispatchers.Main) {
                    (activity as NavigationActivity).loadingDialog.dismiss()
                    books.addAll(result)
                    updateLibraryAdapter()
                }
            } else {
                withContext(Dispatchers.Main) {
                    (activity as NavigationActivity).loadingDialog.dismiss()
                }
            }
        }
    }
}
