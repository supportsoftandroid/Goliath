package com.fantasy.goliath.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fantasy.goliath.databinding.ActivityWelcomeBinding
import com.fantasy.goliath.utility.PreferenceManager

class WelcomeActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
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
            finish()
        }
    }

    private fun initView() {

    }
}
