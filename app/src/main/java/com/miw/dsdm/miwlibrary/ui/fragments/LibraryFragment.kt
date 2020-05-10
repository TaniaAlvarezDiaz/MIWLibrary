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
import com.miw.dsdm.miwlibrary.model.Book
import com.miw.dsdm.miwlibrary.model.Category
import com.miw.dsdm.miwlibrary.ui.activities.BookActivity
import com.miw.dsdm.miwlibrary.ui.activities.NavigationActivity
import com.miw.dsdm.miwlibrary.ui.adapters.LibraryAdapter
import kotlinx.android.synthetic.main.fragment_library.*
import kotlinx.coroutines.*

class LibraryFragment : Fragment() {
    companion object {
        fun newInstance(): LibraryFragment = LibraryFragment()
    }

    private lateinit var libraryAdapter: LibraryAdapter
    private var categories : List<Category> = emptyList()
    private var category = 0L

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
        //TODO Get books from server, call a method (retrofit)
       /* val items = listOf<Book>(
            Book(null, "Libro 1", "Autor 1", "\\r\\n\\r\\nThe Guest Edito", true),
            Book("http://collection.openlibra.com.s3.amazonaws.com/covers/2011/04/disenoAgilConTDD.jpg","Libro 2", "Autor 2", "Descripcion 2 " +
                    "preubadfjdlkfjadlfjdfjdjfdofjdofdjfojdfodjfodjfoajfojfodjfaodjfdofjdofjdofjdofjaofjdofjdfdfdjofjadadadfghddloremipsum esl difd fdof dof fdoijf dojfd fdojfdaf", true),
            Book("http://collection.openlibra.com.s3.amazonaws.com/covers/2011/04/programacion_J2ME_moviles.jpg","Libro 3", "Autor 3", "Descripcion 3", false),
            Book(null,"Libro 4", "Autor 4", "Descripcion 4", false),
            Book(null,"Libro 5", "Autor 5", "Descripcion 5", true)
        )*/
       // val items = emptyList<Book>()

        //SwipeRefreshLayout
       library_swipe_refresh_layout.setOnRefreshListener {
           //TODO get books from server
           library_swipe_refresh_layout.isRefreshing = true
       }

        //Adapter
        /*adapter = LibraryAdapter(items, category) {
            goToBookInformation(it)
        }*/

        //SearchView
       initializeSearchView()

        //Spinner
        //initializeCategoriesSpinner()

        //Recycler
        library_recycler_view.layoutManager = LinearLayoutManager(activity)
    /*    library_recycler_view.adapter = adapter*/

        getBooksCategories()
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
                libraryAdapter.filter.filter(newText)
                return false
            }

        })
    }

    /**
     * Function to initialize categories spinner
     */
    private fun initializeCategoriesSpinner() {
        val adapter = activity?.applicationContext?.let { ArrayAdapter(it, android.R.layout.simple_expandable_list_item_1, categories) }

        library_spinner_categories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                categories[position]
            }
        }
    }

    /**
     * Function to get books categories
     */
    private fun getBooksCategories() {
        (activity as NavigationActivity).loadingDialog.show()
        CoroutineScope(Dispatchers.IO).launch {
            val result = Category.requestAllCategories()
            if (!result.isNullOrEmpty()) {
                categories = result
                initializeCategoriesSpinner()
                getBooks()
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
                //Configure adapter
                libraryAdapter = LibraryAdapter(result, category) { goToBookInformation(it) }
                //Assign adapter to recycler
                library_recycler_view.adapter = libraryAdapter
            } else {
                withContext(Dispatchers.Main) {
                    (activity as NavigationActivity).loadingDialog.dismiss()
                    //TODO show text NO RESULTS
                }
            }
        }
    }

}
