package com.fantasy.goliath.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fantasy.goliath.databinding.ActivityLoginBinding
import com.fantasy.goliath.databinding.FragmentAwardBinding

import com.fantasy.goliath.viewmodal.AwardViewModel
import com.fantasy.goliath.viewmodal.LoginViewModel

class AwardFragment : Fragment() {
    private val viewModal by lazy { ViewModelProvider(this)[AwardViewModel::class.java] }

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentAwardBinding.inflate(layoutInflater)
    }
    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}