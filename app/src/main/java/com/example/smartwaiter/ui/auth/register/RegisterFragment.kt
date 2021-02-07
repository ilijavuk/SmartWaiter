package com.example.smartwaiter.ui.auth.register


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.RegisterRepository
import com.google.common.hash.Hashing
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.btnRegister
import java.nio.charset.StandardCharsets


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: RegisterViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = RegisterRepository()
        val viewModelFactory = RegisterViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        addTextWatcherFun()
        validationCheck()

        val usernameField = editText_username
        val emailField = editText_email

        viewModel.myResponseUsername.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful && it.body() != null) {

                it.body()!!.forEach {
                    if (it.korisnicko_ime == usernameField.text.toString()
                            .trim() && it.email != emailField.text.toString().trim()
                    ) {
                        usernameField.error = "Username exists"
                    } else if (it.email == emailField.text.toString()
                            .trim() && it.korisnicko_ime != usernameField.toString().trim()
                    ) {
                        emailField.error = "Email exists"
                    } else {
                        usernameField.error = "Username exists"
                        emailField.error = "Email exists"
                    }
                }
            } else {
                RegisterSuccess()
            }

        })


        btnRegister.setOnClickListener {
            val username = editText_username.text.toString().trim()
            val email = editText_email.text.toString().trim()
            viewModel.getUsername(
                table = "Korisnik",
                method = "select",
                username = username,
                operator = "or",
                email = email
            )
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

    private fun RegisterSuccess() {
        val firstName = editText_firstName.text.toString().trim()
        val lastName = editText_lastName.text.toString().trim()
        val username = editText_username.text.toString().trim()
        val email = editText_email.text.toString().trim()
        val password = editText_password.text.toString().trim()
        val hashed = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString();
        viewModel.RegisterKorisnik(
            table = "Korisnik",
            method = "insert",
            username = username,
            firstName = firstName,
            lastName = lastName,
            email = email,
            userType = "1",
            password = hashed
        )
        Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
        val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}