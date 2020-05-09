package com.miw.dsdm.miwlibrary.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.model.User
import com.miw.dsdm.miwlibrary.ui.activities.ChangePasswordActivity
import com.miw.dsdm.miwlibrary.ui.activities.NavigationActivity
import kotlinx.android.synthetic.main.fragment_my_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import splitties.alertdialog.appcompat.*

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
        loadUser()
        profile_btn_password.setOnClickListener { startActivity(Intent(activity, ChangePasswordActivity::class.java)) }
    }

    /**
     * It checks whether a user exists.
     * If the credentials are correct, the main page is displayed, but the error is displayed
     */
    private fun loadUser(){
        CoroutineScope(Dispatchers.IO).launch {
            val result = activity?.let { Settings(it).userLoggedIn }?.let { User.requestUserByEmail(it) }
            if (result != null) {
                withContext(Dispatchers.Main) {
                    profile_name_value.text = result.name
                    profile_surname_value.text = result.surname
                    profile_email_value.text = result.email
                }
            }
            else{
                withContext(Dispatchers.Main) {
                    showDialog(getString(R.string.profile_error_alert_title),
                        getString(R.string.profile_error_alert_message))
                }
            }
        }
    }

    private fun showDialog(t: String, m: String){
        activity?.alertDialog {
            title = t
            message = m
            okButton {
            }
        }?.onShow {
            setCancelable(false)
        }?.show()
    }


}
