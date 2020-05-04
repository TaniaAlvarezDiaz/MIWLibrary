package com.miw.dsdm.miwlibrary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.model.Book
import com.miw.dsdm.miwlibrary.ui.adapters.LibraryAdapter
import kotlinx.android.synthetic.main.fragment_library.*

class LibraryFragment : Fragment() {
    companion object {
        fun newInstance(): LibraryFragment = LibraryFragment()
    }

    private lateinit var adapter: LibraryAdapter

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
        val items = listOf<Book>(
            Book(null, "Libro 1", "Autor 1", "Descripcion 1", true),
            Book("http://collection.openlibra.com.s3.amazonaws.com/covers/2011/04/disenoAgilConTDD.jpg","Libro 2", "Autor 2", "Descripcion 2 " +
                    "preubadfjdlkfjadlfjdfjdjfdofjdofdjfojdfodjfodjfoajfojfodjfaodjfdofjdofjdofjdofjaofjdofjdfdfdjofjadadadfghddloremipsum esl difd fdof dof fdoijf dojfd fdojfdaf", true),
            Book("http://collection.openlibra.com.s3.amazonaws.com/covers/2011/04/programacion_J2ME_moviles.jpg","Libro 3", "Autor 3", "Descripcion 3", false),
            Book(null,"Libro 4", "Autor 4", "Descripcion 4", false),
            Book(null,"Libro 5", "Autor 5", "Descripcion 5", true)
        )

        //Adapter
        adapter = LibraryAdapter(items) {
            //TODO complete, go to BookDetail
        }

        //SearchView
       initializeSearchView()

        //Recycler
        library_recycler_view.layoutManager = LinearLayoutManager(activity)
        library_recycler_view.adapter = adapter
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
                adapter.filter.filter(newText)
                return false
            }

        })
    }

}
