package com.miw.dsdm.miwlibrary.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.ui.activities.NavigationActivity
import kotlinx.android.synthetic.main.fragment_my_profile.*

class MyProfileFragment : Fragment() {

    companion object {
        fun newInstance(): MyProfileFragment = MyProfileFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        profile_btn_password.setOnClickListener {
            (activity as NavigationActivity).launchFragment(
                ChangePasswordFragment.newInstance()
            )
        }
    }

}
