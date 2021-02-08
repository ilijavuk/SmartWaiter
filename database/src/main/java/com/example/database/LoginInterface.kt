package com.example.database

import androidx.fragment.app.Fragment

interface LoginInterface {
    fun getFragment(listener : LoginCodeListener): Fragment
}