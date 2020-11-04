package com.example.smartwaiter.ui.register


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.smartwaiter.R
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRegister.setOnClickListener {

            val firstName = editText_firstName.text.toString().trim()
            val lastName = editText_lastName.text.toString().trim()
            val username = editText_username.text.toString().trim()
            val email = editText_email.text.toString().trim()
            val password = editText_password.text.toString().trim()
            val repeatPassword = editText_passwordRepeat.text.toString().trim()

            //viewModel.signUp(firstName, lastName, username, email, password, repeatPassword)
        }
    }


}