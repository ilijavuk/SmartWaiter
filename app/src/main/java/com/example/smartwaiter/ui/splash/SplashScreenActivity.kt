package com.example.smartwaiter.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.asLiveData
import com.example.database.UserPreferences
import com.example.smartwaiter.ui.auth.MainActivity
import com.example.smartwaiter.R
import com.example.smartwaiter.ui.guest.GuestActivity
import com.example.smartwaiter.ui.restaurant.RestaurantActivity
import com.example.smartwaiter.ui.waiter.WaiterActivity
import com.example.smartwaiter.util.startNewActivity

class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val userPreferences = UserPreferences(this)
            userPreferences.userType.asLiveData().observe(this, {
                when(it){
                    "1" -> startNewActivity(GuestActivity::class.java)
                    "2" -> startNewActivity(WaiterActivity::class.java)
                    "3" -> startNewActivity(RestaurantActivity::class.java)
                    else -> startNewActivity(MainActivity::class.java)
                }
                finish()
            })
        }, SPLASH_TIME)
    }
}