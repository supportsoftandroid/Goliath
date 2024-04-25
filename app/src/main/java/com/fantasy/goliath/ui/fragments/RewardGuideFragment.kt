package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fantasy.goliath.databinding.FragmentRewardGuideBinding
import com.fantasy.goliath.ui.base.BaseFragment


class RewardGuideFragment : BaseFragment() {

    private val binding by lazy { FragmentRewardGuideBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)



        binding.let {
            initView()
            clickListener()
        }

        return binding.root


    }

    private fun clickListener() {
        binding.btnStart.setOnClickListener(){
           addFragment(GameGuideFragment())

        }
    }

    private fun initView() {

    }
}
