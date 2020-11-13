package com.example.smartwaiter.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.database.UserPreferences
import com.example.smartwaiter.ui.auth.MainActivity
import com.example.smartwaiter.R
import com.example.smartwaiter.ui.restaurant.RestaurantActivity
import com.example.smartwaiter.util.startNewActivity

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val userPreferences = UserPreferences(this)
            userPreferences.authToken.asLiveData().observe(this, Observer {
                val activity = if(it == null) MainActivity::class.java else RestaurantActivity::class.java
                startNewActivity(activity)
                finish()
            })
        },SPLASH_TIME)
    }
}