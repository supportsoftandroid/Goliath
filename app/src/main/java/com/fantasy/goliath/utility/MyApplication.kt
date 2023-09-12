package com.fantasy.goliath.utility

import android.app.Application
/*import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp*/

import com.fantasy.goliath.R

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
       /* FirebaseApp.initializeApp(this)
        if (!Places.isInitialized())
            Places.initialize(
                this, resources.getString(R.string.gmp_key)
            )*/
    }
}