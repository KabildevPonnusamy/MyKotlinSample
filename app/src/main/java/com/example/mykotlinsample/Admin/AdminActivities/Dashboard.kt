package com.example.mykotlinsample.Admin.AdminActivities

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.mykotlinsample.Admin.AdminFragments.CategoryFragment
import com.example.mykotlinsample.Admin.AdminFragments.SecondFragment
import com.example.mykotlinsample.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.admin_dashboard.*
import kotlinx.android.synthetic.main.app_bar_main.*

class Dashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_dashboard)
        supportActionBar ?.hide()

        val toggle = ActionBarDrawerToggle(this,  drawer_layout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        } else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        }

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        displayScreen(-1)
                }

    fun displayScreen(id: Int) {
        val fragment = when(id) {

            R.id.action_cut -> {
                CategoryFragment()
                    }

            R.id.action_copy -> {
                SecondFragment()
                    }

            else -> {
                CategoryFragment()
                   }

                }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.relativeLayout, fragment)
            .commit()
           }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        displayScreen(menuItem.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
            }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
                   } else {
            super.onBackPressed()
                }
            }

}