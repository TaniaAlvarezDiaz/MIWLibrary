package com.miw.dsdm.miwlibrary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.ui.activities.NavigationActivity
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : Fragment() {

    companion object {
        fun newInstance(): ChangePasswordFragment = ChangePasswordFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
    }

    private fun initialize() {
    }

}
