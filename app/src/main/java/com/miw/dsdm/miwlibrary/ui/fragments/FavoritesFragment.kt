package com.miw.dsdm.miwlibrary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miw.dsdm.miwlibrary.R

class FavoritesFragment : Fragment() {
    companion object {
        fun newInstance(): FavoritesFragment = FavoritesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_favorites, container, false)
}
