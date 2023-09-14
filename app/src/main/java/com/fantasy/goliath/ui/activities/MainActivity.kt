package com.fantasy.goliath.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ActivityMainBinding
import com.fantasy.goliath.ui.fragments.AwardFragment
import com.fantasy.goliath.ui.fragments.HomeFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.StaticData
import com.fantasy.goliath.utility.StaticData.Companion.showToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var navView: BottomNavigationView
        fun hideNavigationTab() {
            navView.visibility = View.GONE
        }

        fun visibleNavigationTab() {
            navView.visibility = View.VISIBLE
        }

        fun navProfileTab() {
            navView.selectedItemId = R.id.nav_profile
        }
    }
    private lateinit var binding: ActivityMainBinding
    var doubleBackToExitPressedOnce: Boolean = false
    var isHomeFragment: Boolean = true
    lateinit var mContext: Context
    lateinit var preferenceManager:PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StaticData.adjustFontScale(this, resources.configuration, 1.0f)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext=this@MainActivity
        preferenceManager = PreferenceManager(mContext)
        initView()
        clickListener()
        navView.selectedItemId = R.id.nav_home
    }

    private fun initView() {
        navView  = binding.navView
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do custom work here
                // if you want onBackPressed() to be called as normal afterwards

                // isEnabled = false
                onBackPressed()

            }
        })
    }
    private fun clickListener() {
        navView.setOnItemSelectedListener {
            isHomeFragment = false
            when (it.itemId) {
                R.id.nav_home -> {
                    isHomeFragment = true
                    StaticData.replaceFragment(mContext, HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_award -> {
                    StaticData.replaceFragment(mContext, AwardFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_result -> {
                    StaticData.replaceFragment(mContext, AwardFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_profile -> {
                    StaticData.replaceFragment(mContext, AwardFragment())
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
            if (backStackEntryCount == 1) {
                navView.visibility = View.VISIBLE
                //  super.onBackPressed();
            } else {
                navView.visibility = View.GONE
                //  super.onBackPressed();
            }
        } else {

            navView.visibility = View.VISIBLE
            if (!isHomeFragment) {
                StaticData.changeStatusBarColor(this, "home")
                navView.selectedItemId = R.id.nav_home
                doubleBackToExitPressedOnce = false
                //StaticData.replaceFragment(mContext, HomeFragment())
            } else {
                if (doubleBackToExitPressedOnce) {
                    finish()
                } else {
                    doubleBackToExitPressedOnce = true
                    showToast(applicationContext, getString(R.string.press_again_to_exit_from_app))
                    GlobalScope.launch {
                        delay(2000)
                        doubleBackToExitPressedOnce = false
                    }
                }
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}