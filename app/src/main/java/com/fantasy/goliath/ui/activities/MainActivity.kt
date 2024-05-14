package com.fantasy.goliath.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View


import androidx.core.view.isVisible
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.ActivityMainBinding
import com.fantasy.goliath.ui.base.BaseActivity
import com.fantasy.goliath.ui.fragments.nav.AwardFragment
import com.fantasy.goliath.ui.fragments.nav.HomeFragment
import com.fantasy.goliath.ui.fragments.nav.MatchOngoingFragment
import com.fantasy.goliath.ui.fragments.nav.ProfileFragment

import com.fantasy.goliath.utility.adjustFontScale

import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity() {
    companion object {
        lateinit var navView: BottomNavigationView
        fun hideNavigationTab() {
            //navView.visibility = View.GONE
        }

        fun visibleNavigationTab() {
            navView.visibility = View.VISIBLE
        }

        fun navProfileTab() {
            navView.selectedItemId = R.id.nav_profile
        }
        fun navMyPredictionTab() {
            navView.selectedItemId = R.id.nav_profile
        }
    }
    private lateinit var binding: ActivityMainBinding
    var doubleBackToExitPressedOnce: Boolean = false
    var isHomeFragment: Boolean = true
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(this, resources.configuration, 1.0f)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mContext=this@MainActivity
        setHavingFragments()
        setMinimumBackstackCount(0)
        initView()
        clickListener()
        navView.selectedItemId = R.id.nav_home
        if (intent.getStringExtra("from") != null)  {
            if (intent.getStringExtra("from").equals("my_prediction",true)) {
                navView.selectedItemId = R.id.nav_result
            }
        }
    }

    private fun initView() {
        navView  = binding.navView
        /*onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do custom work here
                // if you want onBackPressed() to be called as normal afterwards

                // isEnabled = false
                onBackPressed()

            }
        })*/
    }
    private fun clickListener() {
        navView.setOnItemSelectedListener {
            isHomeFragment = false
            when (it.itemId) {
                R.id.nav_home -> {
                    isHomeFragment = true
                    replaceFragment( HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_award -> {
                    replaceFragment( AwardFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_result -> {
                    replaceFragment( MatchOngoingFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.nav_profile -> {
                   replaceFragment( ProfileFragment())
                    return@setOnItemSelectedListener true
                }

            }
            false
        }
    }
  /*  @Deprecated("Deprecated in Java")
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
              changeStatusBarColor(this, "home")
                navView.selectedItemId = R.id.nav_home
                doubleBackToExitPressedOnce = false
                //UiUtils.replaceFragment(mContext, HomeFragment())
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
    }*/

    override fun selectedBottomNavOption(id: Int) {
        binding.navView.selectedItemId = id
    }

    override fun willBottomNavVisible(isVisible: Boolean) {

        binding.navView.isVisible=isVisible
        binding.navView.isVisible=isVisible
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}