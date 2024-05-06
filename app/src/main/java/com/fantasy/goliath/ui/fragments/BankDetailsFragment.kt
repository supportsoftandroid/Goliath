package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentAddBankDetailsBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.ProfileViewModel

class BankDetailsFragment : BaseFragment() {
    companion object {
        fun newInstance(from: String): BankDetailsFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = BankDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModal by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentAddBankDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var loginResponse: LoginResponse
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.let {
            initView()
            clickListener()
        }
        return binding.root
    }

    private fun clickListener() {
        binding.viewHeader.setClickListener(this)
        binding.viewHeader.getToolBarView().imgMenu1.isVisible=false
        binding.viewHeader.getToolBarView().imgMenu2.isVisible=false
        /*binding.viewHeader.getToolBarView().imgBack.setOnClickListener {
            onBackPressed()
        }*/
     /*   binding.btnBack.setOnClickListener() {
           onBackPressed()
        }*/

    }

    fun initView() {

        binding.viewHeader.setTitle(requireActivity().getString(R.string.add_bank_details))
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onNotificationsIconClick() {
        super.onNotificationsIconClick()
    }

    override fun onWalletIconClick() {
        super.onWalletIconClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}