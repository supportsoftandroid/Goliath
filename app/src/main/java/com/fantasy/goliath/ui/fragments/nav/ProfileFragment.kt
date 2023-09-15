package com.fantasy.goliath.ui.fragments.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.goliath.R

import com.fantasy.goliath.databinding.FragmentProfileBinding
import com.fantasy.goliath.model.CommonDataItem
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.activities.MainActivity
import com.fantasy.goliath.ui.adapter.ProfileAdapter
import com.fantasy.goliath.ui.fragments.ChangePasswordFragment
import com.fantasy.goliath.ui.fragments.EditProfileFragment
import com.fantasy.goliath.ui.fragments.MatchHistoryFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.StaticData
import com.fantasy.goliath.utility.UtilsManager

import com.fantasy.goliath.viewmodal.ProfileViewModel

class ProfileFragment : Fragment() {
    private val viewModal by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    lateinit var adaper: ProfileAdapter
    var dataList = mutableListOf<CommonDataItem>()
    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    private lateinit var loginResponse: LoginResponse
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = binding.root
        binding.let{ initView() }
        return root
    }
    fun initView() {
        //setProfileData(loginResponse.user)
        dataList.clear()

        dataList.add(CommonDataItem("History", "View for matches", false))
        dataList.add(CommonDataItem("My info & Settings", "Edit your info & additional information", false))
        dataList.add(CommonDataItem("Change Password", "Manage your password change for more security", false))
        dataList.add(CommonDataItem("Transaction History", "View your past transaction", false))
        dataList.add(CommonDataItem("Logout", "Exit from application", false))


        adaper =
            ProfileAdapter(requireActivity(), dataList, { pos, type -> onAdapterClick(pos, type) })
        binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvList.adapter = adaper

    }

    private fun onAdapterClick(pos: Int, type: String) {
        if (type.equals("History")){
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(
                requireActivity(),
                MatchHistoryFragment.newInstance("add")
            )

        }else if (type.equals("My info & Settings")){
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(
                requireActivity(),
                EditProfileFragment.newInstance("add")
            )
        }else if (type.equals("Change Password")){
            MainActivity.hideNavigationTab()
            StaticData.backStackAddFragment(
                requireActivity(),
                ChangePasswordFragment.newInstance("add")
            )
        }else if (type.equals("Transaction History")){
            logOutFromApp()
        }else if (type.equals("Logout")){
            logOutFromApp()
        }

    }

    private fun logOutFromApp() {
        val builder = AlertDialog.Builder(requireActivity())
        // builder.setTitle(R.string.app_name)
        builder.setMessage(R.string.logout_message)

        builder.setPositiveButton(getString(R.string.yes)) { dialogInterface, which ->
          StaticData.logoutFromApp(requireActivity())


        }
        //performing negative action
        builder.setNegativeButton(getString(R.string.no)) { dialogInterface, which -> }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}