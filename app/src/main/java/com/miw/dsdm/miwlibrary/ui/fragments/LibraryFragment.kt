package com.miw.dsdm.miwlibrary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        //TODO Get books from server
        val items = listOf<Book>(
            Book("Libro 1", "Autor 1", "Descripcion 1", true),
            Book("Libro 2", "Autor 2", "Descripcion 2 " +
                    "preubadfjdlkfjadlfjdfjdjfdofjdofdjfojdfodjfodjfoajfojfodjfaodjfdofjdofjdofjdofjaofjdofjdfdfdjofjadadadfghddloremipsum", true),
            Book("Libro 3", "Autor 3", "Descripcion 3", false),
            Book("Libro 4", "Autor 4", "Descripcion 4", false),
            Book("Libro 5", "Autor 5", "Descripcion 5", true)
        )

        library_recycler_view.layoutManager = LinearLayoutManager(activity)
        library_recycler_view.adapter = LibraryAdapter(items) {
            //TODO launch DetailFragment
        }
    }

}
