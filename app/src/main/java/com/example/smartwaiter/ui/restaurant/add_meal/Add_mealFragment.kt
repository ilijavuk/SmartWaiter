package com.example.smartwaiter.ui.restaurant.add_meal

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.Uri.*
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.OpenableColumns
import android.service.autofill.Validators.not
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.smartwaiter.R
import hr.foi.air.webservice.Webservice
import kotlinx.android.synthetic.main.fragment_add_meal.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.*
import java.sql.Timestamp


class Add_mealFragment: Fragment(R.layout.fragment_add_meal) {

    val test = 1
}


