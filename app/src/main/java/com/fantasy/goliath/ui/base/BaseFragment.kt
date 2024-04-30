package com.fantasy.goliath.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.fantasy.goliath.R
import com.fantasy.goliath.ui.activities.AuthActivity
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.ui.components.MyToolBar
import com.fantasy.goliath.ui.fragments.NotificationsFragment
import com.fantasy.goliath.ui.fragments.WalletDetailsFragment

import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.utility.loadImage
import com.fantasy.goliath.utility.loadProfileImage
import com.fantasy.goliath.utility.showAlertDialogMessageError
import com.fantasy.goliath.utility.showAlertMessageError

import com.fantasy.goliath.utility.showErrorToast



open class BaseFragment : Fragment(), MyToolBar.MyToolBarListener {
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    lateinit var activity: Activity

    companion object {
        const val FROM = "from"
        const val TITLE = "title"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        activity = requireActivity()
        preferenceManager = PreferenceManager(activity)
        utilsManager = UtilsManager(activity)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun addFragmentToBackStack(fragment: Fragment) {
        isBaseActivity { it.addFragmentToBackStack(fragment) }
    }

    fun addFragment(fragment: Fragment) {
        isBaseActivity { it.addFragment(fragment) }
    }
    fun replaceFragment(fragment: Fragment) {
        isBaseActivity { it.replaceFragment(fragment) }
    }
    fun replaceFragmentAddBackStack(fragment: Fragment) {
        isBaseActivity { it.replaceFragmentToBackStack(fragment) }
    }

    fun popFragmentUpTo(fragmentCls: Class<*>, isInclusive: Boolean = false) {
        isBaseActivity { it.popFragmentUpTo(fragmentCls, isInclusive) }
    }

    private fun isBaseActivity(callback: (BaseActivity) -> Unit) {
        if (this::activity.isInitialized && activity is BaseActivity) {
            callback.invoke(activity as BaseActivity)
        }
    }


    fun selectBottomNavMenu(id: Int) {
        (activity as? MainActivity)?.selectedBottomNavOption(id)
    }

    fun gotoMainActivity() {
        startActivity(Intent(activity, MainActivity::class.java))
        activity.finishAffinity()
    }
    fun gotoAuthActivity() {
        startActivity(Intent(activity, AuthActivity::class.java))
        activity.finishAffinity()
    }

    fun getTitle(): String = arguments?.getString(TITLE, "") ?: getString(R.string.app_name)
    fun getFrom(): String = arguments?.getString(FROM, "") ?: ""

    fun showErrorToast(msg: String) = activity.showErrorToast(msg)
    fun showErrorToast(msgResId: Int) = activity.showErrorToast(getString(msgResId))
    fun loadImage(url: String,imageView: ImageView) = activity.loadImage(url,imageView)
    fun loadProfileImage(url: String,imageView: ImageView) = activity.loadProfileImage(url,imageView)
    fun showAlertMessageError(msg: String) = activity.showAlertDialogMessageError(msg)



    override fun onStart() {
        isBaseActivity { it.getTopFragmentIsOfBottomNav() }
        super.onStart()
    }

    override fun onPause() {
        isBaseActivity { it.getTopFragmentIsOfBottomNav() }
        super.onPause()
    }

    override fun onBackPressed() = requireActivity().onBackPressedDispatcher.onBackPressed()
    override fun onWalletIconClick() {
        addFragmentToBackStack(
            WalletDetailsFragment.newInstance("add")
        )
    }

    override fun onNotificationsIconClick() {
        addFragmentToBackStack(
            NotificationsFragment()
        )
    }

}