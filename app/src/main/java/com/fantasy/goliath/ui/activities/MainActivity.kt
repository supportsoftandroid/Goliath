package com.fantasy.goliath.ui.activities

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var navView: BottomNavigationView
        fun hideNavigationTab() {
            navView.visibility = View.GONE
        }

        fun visibleNavigationTab() {
            navView.visibility = View.VISIBLE
        }

        fun navPostJobTab() {
           // navView.selectedItemId = R.id.nav_post_job
        } fun navAllJobTab() {
          //  navView.selectedItemId = R.id.nav_all_jobs
        }

        fun navAssignJobTab() {
          //  navView.selectedItemId = R.id.nav_assigned_job
        }
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView  = binding.navView


    }
}