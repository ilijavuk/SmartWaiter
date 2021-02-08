package com.example.smartwaiter.util

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwaiter.ui.auth.login.HomeFragment
import com.google.android.material.snackbar.Snackbar
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.order_list_item.view.*

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}


fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}


fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar("Check your internet connection", retry)
        else -> {
            if (this is HomeFragment) {
                requireView().editTextUsername.error = "Wrong username"
                requireView().editTextPassword.error = "Wrong password"
            }
        }
    }
}
