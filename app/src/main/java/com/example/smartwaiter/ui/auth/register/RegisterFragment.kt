package com.example.smartwaiter.ui.auth.register


import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.util.ObjectsCompat.hash
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Repository
import com.example.smartwaiter.ui.home.HomeFragmentDirections
import com.example.smartwaiter.ui.home.HomeViewModel
import com.example.smartwaiter.ui.home.HomeViewModelFactory
import com.google.common.hash.Hashing

import hr.foi.air.webservice.Webservice
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.btnRegister
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okio.HashingSource
import java.nio.charset.StandardCharsets
import java.util.Objects.hash


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: RegisterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = RegisterViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        addTextWatcherFun()
        validationCheck()

        btnRegister.setOnClickListener {
            val firstName = editText_firstName.text.toString().trim()
            val lastName = editText_lastName.text.toString().trim()
            val username = editText_username.text.toString().trim()
            val email = editText_email.text.toString().trim()
            val password = editText_password.text.toString().trim()

            val hashed = Hashing.sha256()
            .hashString(password, StandardCharsets.UTF_8)
            .toString();



            viewModel.RegisterKorisnik(
                table = "Korisnik", method = "insert", username,
                firstName, lastName, email, "1", hashed
            )

            Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()

            val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
            findNavController().navigate(action)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            validationCheck()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }


    private fun addTextWatcherFun() {
        editText_firstName.addTextChangedListener(textWatcher)
        editText_lastName.addTextChangedListener(textWatcher)
        editText_username.addTextChangedListener(textWatcher)
        editText_email.addTextChangedListener(textWatcher)
        editText_password.addTextChangedListener(textWatcher)
        editText_passwordRepeat.addTextChangedListener(textWatcher)
    }


    private fun validationCheck() {
        val firsName: String = editText_firstName.text.toString()
        val lastName: String = editText_lastName.text.toString()
        val username: String = editText_username.text.toString()
        val email: String = editText_email.text.toString()
        val password: String = editText_password.text.toString()
        val passwordRepeat: String = editText_passwordRepeat.text.toString()

        val emailPattern: Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()


        if (firsName.equals("") || lastName.equals("") || username.equals("")
            || email.equals("") || password.equals("") || passwordRepeat.equals("")
        )
            btnRegister.isEnabled = false
        else if (password != passwordRepeat)
            btnRegister.isEnabled = false
        else if (!email.matches(emailPattern))
            btnRegister.isEnabled = false
        else
            btnRegister.isEnabled = true
    }
}