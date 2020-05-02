package com.miw.dsdm.miwlibrary.ui.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.SharedPreferenceStorage
import com.miw.dsdm.miwlibrary.ui.fragments.LibraryFragment
import kotlinx.android.synthetic.main.activity_navigation.*
import splitties.alertdialog.appcompat.*

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        initialize()
    }

    override fun onBackPressed() {
        //Close left menu
        if (navigation_drawer.isDrawerOpen(GravityCompat.START)) {
            navigation_drawer.closeDrawer(GravityCompat.START)
        } else {
            //Go before fragment if possible
            if (supportFragmentManager.backStackEntryCount > 0)
                supportFragmentManager.popBackStack()
        }
    }

    /**
     * Function to initialize components
     */
    private fun initialize() {
        val toggle = ActionBarDrawerToggle(
            this,
            navigation_drawer,
            navigation_toolbar,
            R.string.navigation_close_drawer,
            R.string.navigation_open_drawer
        )
        navigation_drawer.addDrawerListener(toggle)
        toggle.isDrawerIndicatorEnabled = true
        toggle.drawerArrowDrawable.color = Color.BLACK
        toggle.syncState()

        //Left menu
        navigation_left_menu.setNavigationItemSelectedListener {
            launchFragmentBottomNavigation(it.itemId)
            it.isChecked = true
            navigation_drawer.closeDrawers()
            true
        }

        //Left menu footer
        navigation_left_menu_footer.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_sign_out -> {
                    signOut()
                }
            }
            it.isChecked = true
            navigation_drawer.closeDrawers()
            true
        }

        //Bottom menu
        navigation_bottom_menu.setOnNavigationItemSelectedListener {
            launchFragmentBottomNavigation(it.itemId)
            it.isChecked = true
            navigation_drawer.closeDrawers()
            true
        }
    }

    /**
     * Function to sign out
     */
    private fun signOut() {
        alertDialog {
            message = getString(R.string.navigation_sign_out_alert_message)
            okButton {
                //Remove preferences
                SharedPreferenceStorage(this@NavigationActivity).removeUserLoggedInt()

                //Go to Login
                val intent = Intent(this@NavigationActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish();
            }
            cancelButton { dialog -> dialog.cancel() }
        }.show()
    }

    /**
     * Function to launch one of the fragments from the bottom navigation
     */
    private fun launchFragmentBottomNavigation(id: Int) {
        navigation_left_menu.setCheckedItem(id)

        val fragment = when (id) {
            R.id.navigation_library -> {
                LibraryFragment()
            }
            R.id.navigation_favorites -> {
                LibraryFragment()
            }
            else -> {
                LibraryFragment()
            }
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(fragment_container.id, fragment)
        fragmentTransaction.commit()
    }

}
