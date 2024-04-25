package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentEditProfileBinding
import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.ui.activities.StaticPagesActivity
import com.fantasy.goliath.ui.base.BaseFragment
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager
import com.fantasy.goliath.viewmodal.ProfileViewModel

class EditProfileFragment : BaseFragment() {
    companion object {
        fun newInstance(from: String): EditProfileFragment {
            val args = Bundle()
            args.putString("from", from)
            val fragment = EditProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private val viewModal by lazy { ViewModelProvider(this)[ProfileViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentEditProfileBinding.inflate(layoutInflater)
    }

    private lateinit var loginResponse: LoginResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val root: View = binding.root

        binding.let {
            initView()
            clickListener()
        }
        return root
    }

    private fun clickListener() {
        binding.viewHeader.setClickListener(this)

        binding.btnSubmit.setOnClickListener() {

           onBackPressed()

        }
      /*  binding.tvTerms.setOnClickListener {
            StaticPagesActivity.newInstance(
                requireActivity(),
                binding.tvTerms.text.toString(),
                "Terms"
            )


        }
        binding.tvPrivacy.setOnClickListener {
            StaticPagesActivity.newInstance(
                requireActivity(),
                binding.tvPrivacy.text.toString(),
                "Privacy"
            )

        }
        binding.tvHelp.setOnClickListener {
            StaticPagesActivity.newInstance(
                requireActivity(),
                binding.tvHelp.text.toString(),
                "Help"
            )

        }
        binding.tvAbout.setOnClickListener {
            StaticPagesActivity.newInstance(
                requireActivity(),
                binding.tvAbout.text.toString(),
                "About"
            )

        }*/
    }

    fun initView() {

        binding.viewHeader.setTitle(   requireActivity().getString(R.string.my_info_settings))
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