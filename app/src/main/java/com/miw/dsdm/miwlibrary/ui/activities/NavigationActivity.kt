package com.miw.dsdm.miwlibrary.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.miw.dsdm.miwlibrary.R
import com.miw.dsdm.miwlibrary.data.storage.local.Settings
import com.miw.dsdm.miwlibrary.ui.fragments.FavoritesFragment
import com.miw.dsdm.miwlibrary.ui.fragments.LibraryFragment
import com.miw.dsdm.miwlibrary.ui.fragments.MyProfileFragment
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
        //Toolbar
        initializeToolbar()

        //Drawer
        initializeDrawer()

        //Left menu
        initializeLeftMenu()

        //Bottom menu
        initializeBottomMenu()

        //Open LibraryFragment by default
        navigation_bottom_menu.selectedItemId = R.id.navigation_library
    }

    /**
     * Function to initialize toolbar
     */
    private fun initializeToolbar() {
        navigation_toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.navigation_sign_out -> signOut()
            }
            true
        }
    }

    /**
     * Function to initialize drawer
     */
    private fun initializeDrawer() {
        //Drawer
        val toggle = ActionBarDrawerToggle(
            this,
            navigation_drawer,
            navigation_toolbar,
            R.string.navigation_close_drawer,
            R.string.navigation_open_drawer
        )
        toggle.isDrawerIndicatorEnabled = true
        toggle.drawerArrowDrawable.color = Color.BLACK
        toggle.syncState()
        navigation_drawer.addDrawerListener(toggle)
    }

    /**
     * Function to initialize left menu
     */
    private fun initializeLeftMenu() {
        //Necessary to bring the NavigationView to the front. It could be ignored in the case that
        //this component is the last element of the layout, in this case, if it were placed under the ConstraintLayout
        navigation_left_menu.bringToFront()

        navigation_left_menu.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_sign_out -> signOut()
                else -> launchFragmentBottomNavigation(it.itemId, true)
            }
            navigation_drawer.closeDrawers()
            true
        }
    }

    /**
     * Function to initialize bottom menu
     */
    private fun initializeBottomMenu() {
        navigation_bottom_menu.setOnNavigationItemSelectedListener {
            launchFragmentBottomNavigation(it.itemId, false)
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
                Settings(this@NavigationActivity).clearAll()

                //Go to Login
                val intent = Intent(this@NavigationActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish();
            }
            cancelButton { dialog -> dialog.cancel() }
        }.onShow {
            setCancelable(false)
        }.show()
    }

    /**
     * Function to launch one of the fragments from the bottom navigation
     */
    private fun launchFragmentBottomNavigation(id: Int, leftMenu: Boolean) {
        if (leftMenu) navigation_bottom_menu.selectedItemId = id
        else navigation_left_menu.setCheckedItem(id)

        val fragment = when (id) {
            R.id.navigation_library -> {
                LibraryFragment.newInstance()
            }
            R.id.navigation_favorites -> {
                FavoritesFragment.newInstance()
            }
            else -> {
                MyProfileFragment.newInstance()
            }
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(fragment_container.id, fragment)
        fragmentTransaction.commit()
    }

}
