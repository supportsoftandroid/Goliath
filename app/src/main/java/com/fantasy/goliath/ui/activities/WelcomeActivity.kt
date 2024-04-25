package com.fantasy.goliath.ui.activities

import android.content.Intent
import android.os.Bundle
import com.fantasy.goliath.databinding.ActivityWelcomeBinding
import com.fantasy.goliath.ui.base.BaseActivity
import com.fantasy.goliath.utility.adjustFontScale

class WelcomeActivity : BaseActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        adjustFontScale(this@WelcomeActivity, resources.configuration, 1.0f)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.let {
            initView()
            clickListener()
        }

    }

    private fun clickListener() {
        binding.btnStart.setOnClickListener(){
            startActivity(Intent(this, AuthActivity::class.java).putExtra("from","login"))
            finishAffinity()

        }

        binding.tvSignup.setOnClickListener(){
            startActivity(Intent(this, AuthActivity::class.java).putExtra("from","sign_up"))
            finishAffinity()

        }
    }

    private fun initView() {

    }
}
