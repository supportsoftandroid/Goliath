package com.fantasy.goliath.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fantasy.goliath.databinding.ActivityWelcomeBinding
import com.fantasy.goliath.utility.PreferenceManager
import com.fantasy.goliath.utility.StaticData

class WelcomeActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        StaticData.adjustFontScale(this, resources.configuration, 1.0f)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        preferenceManager = PreferenceManager(this)
        binding.let {
            initView()
            clickListener()
        }

    }

    private fun clickListener() {
        binding.btnStart.setOnClickListener(){
            startActivity(Intent(this, LoginActivity::class.java))

        }

        binding.tvSignup.setOnClickListener(){
            startActivity(Intent(this, SignupActivity::class.java))

        }
    }

    private fun initView() {

    }
}
