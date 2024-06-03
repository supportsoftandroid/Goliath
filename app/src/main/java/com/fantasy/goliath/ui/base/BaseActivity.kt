package com.fantasy.goliath.ui.base


import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.fantasy.goliath.R
import com.fantasy.goliath.ui.fragments.nav.HomeFragment
import com.fantasy.goliath.utility.adjustFontScale
import com.fantasy.goliath.utility.askIsNotificationPermission


import com.fantasy.goliath.utility.safeCall
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.Manifest
import com.fantasy.goliath.ui.fragments.nav.LeaderBoardFragment
import com.fantasy.goliath.ui.fragments.nav.MatchOngoingFragment
import com.fantasy.goliath.ui.fragments.nav.ProfileFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.hideKeyboard


open class BaseActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    private var havingFragments: Boolean = false
    private var doubleBackToExitPressedOnce: Boolean = false
    private var minimumBackStackCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adjustFontScale(this@BaseActivity, resources.configuration, 1.0f)
        preferenceManager = PreferenceManager(this)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleOnBackPressedCall()
            }
        })

        if (!askIsNotificationPermission(this)&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

        }
    }


    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // FCM SDK (and your app) can post notifications.
            } else {
                // TODO: Inform user that that your app will not show notifications.
            }
        }
    open fun selectedBottomNavOption(id: Int) {}


    fun addFragmentToBackStack(fragment: Fragment) = safeCall {
        hideKeyboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.simpleName.toString()
       // printLog("ADDED_FRAGMENT_NAME", tag)
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        fragmentTransaction.add(R.id.frame, fragment, tag).addToBackStack(tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun addFragment(fragment: Fragment) = safeCall {
        hideKeyboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.simpleName.toString()
        //printLog("ADDED_FRAGMENT_NAME", tag)
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        fragmentTransaction.add(R.id.frame, fragment, tag)
        fragmentTransaction.commit()
    }


    fun replaceFragment(fragment: Fragment) {
        hideKeyboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.simpleName.toString()
      //  printLog("REPLACED_FRAGMENT_NAME", tag)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.replace(R.id.frame, fragment, tag)
        fragmentTransaction.commit()
    }

    fun replaceFragmentToBackStack(fragment: Fragment) {
        hideKeyboard()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val tag = fragment.javaClass.simpleName.toString()
       // printLog("REPLACED_FRAGMENT_NAME", tag)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        fragmentTransaction.replace(R.id.frame, fragment, tag).addToBackStack(tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun popFragmentUpTo(fragmentCls: Class<*>, isInclusive: Boolean = false) = safeCall {
        hideKeyboard()
        supportFragmentManager.popBackStack(
            fragmentCls.simpleName.toString(),
            if (isInclusive) FragmentManager.POP_BACK_STACK_INCLUSIVE else 0
        )
    }


    fun getTopFragmentIsOfBottomNav() {
        var topFragment = ""
        safeCall {
            topFragment = supportFragmentManager.fragments.lastOrNull()?.tag.toString().trim()
        }
        val willVisible = topFragment.isNotBlank() && supportFragmentManager.backStackEntryCount < 1 &&
                (topFragment == HomeFragment::class.java.simpleName.toString().trim()
              || topFragment == LeaderBoardFragment::class.java.simpleName.toString().trim()
              || topFragment == MatchOngoingFragment::class.java.simpleName.toString().trim()
             || topFragment == ProfileFragment::class.java.simpleName.toString().trim() )
     //   printLog("topFragment",topFragment.toString())
      //  printLog("willVisible",willVisible.toString())
        willBottomNavVisible(willVisible)
    }

    open fun willBottomNavVisible(isVisible: Boolean) {}


    open fun setHavingFragments() {
        havingFragments = true
    }

    fun setMinimumBackstackCount(count: Int) {
        minimumBackStackCount = if (count < 0) 0 else count
    }


    /*@Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
    }*/

    private fun handleOnBackPressedCall() {
        hideKeyboard()
      //  printLog("havingFragments",havingFragments.toString())
      //  printLog("minimumBackStackCount",minimumBackStackCount.toString())
        if (havingFragments) {
            if (supportFragmentManager.backStackEntryCount > minimumBackStackCount) {
                supportFragmentManager.popBackStackImmediate()
                return
            }
        }
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
        } else {
            doubleBackToExitPressedOnce = true
            Toast.makeText(
                this, getString(R.string.press_again_to_exit_from_app), Toast.LENGTH_SHORT
            ).show()
            lifecycleScope.launch {
                delay(2000)
                doubleBackToExitPressedOnce = false
            }
            return
        }
    }




}