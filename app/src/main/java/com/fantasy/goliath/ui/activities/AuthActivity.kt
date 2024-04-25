package com.fantasy.goliath.ui.activities

import android.os.Bundle
import com.fantasy.goliath.R
import com.fantasy.goliath.ui.base.BaseActivity
import com.fantasy.goliath.ui.fragments.LoginFragment
import com.fantasy.goliath.ui.fragments.SignupFragment


class AuthActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setHavingFragments()
        addFragmentToBackStack(LoginFragment())
        val from=intent.extras?.getString("from").toString()
        if (from.equals("sign_up",true)){
            addFragmentToBackStack(SignupFragment())
        }
    }


}