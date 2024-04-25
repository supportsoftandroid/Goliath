package com.fantasy.goliath.utility

/*import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp*/

import android.app.Application

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