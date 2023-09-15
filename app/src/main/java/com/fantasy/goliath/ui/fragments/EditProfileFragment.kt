package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.R
import com.fantasy.goliath.databinding.FragmentChangePasswordBinding
import com.fantasy.goliath.databinding.FragmentEditProfileBinding

import com.fantasy.goliath.model.LoginResponse
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.UtilsManager

import com.fantasy.goliath.viewmodal.ProfileViewModel

class EditProfileFragment : Fragment() {
    companion object {
        fun newInstance(from: String ): EditProfileFragment {
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


    lateinit var preferenceManager: PreferenceManager
    lateinit var utilsManager: UtilsManager
    private lateinit var loginResponse: LoginResponse
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = binding.root
        binding.let{ initView()
            clickListener()
        }
        return root
    }
    private fun clickListener() {

        binding.btnBack.setOnClickListener() {

            requireActivity().onBackPressed()

        }
        binding.btnSubmit.setOnClickListener() {

            requireActivity().onBackPressed()

        }

    }
    fun initView() {

        binding.viewHeader.txtTitle.text=requireActivity().getString(R.string.my_info_settings)
    }





    override fun onDestroyView() {
        super.onDestroyView()

    }
}