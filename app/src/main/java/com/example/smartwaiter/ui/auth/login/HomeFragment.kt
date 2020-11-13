package com.example.smartwaiter.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.AuthRepository
import com.example.smartwaiter.ui.restaurant.RestaurantActivity
import com.example.smartwaiter.util.enable
import com.example.smartwaiter.util.startNewActivity
import com.example.smartwaiter.util.visible
import com.google.common.hash.Hashing
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBarLogin.visible(false)
        btnLogin.enable(false)

        userPreferences = UserPreferences(requireContext())

        val repository = AuthRepository(userPreferences)
        val viewModelFactory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val usernameFiend = editTextUsername
        val passwordFiend = editTextPassword

        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            progressBarLogin.visible(false)
            if(it.isSuccessful && it.body() != null){

                Log.d("Response", it.body().toString())
                lifecycleScope.launch {
                    viewModel.saveAuthToken(it.body()!![0].korisnicko_ime)
                    viewModel.saveUserType(it.body()!![0].tip_korisnika_id)
                    requireActivity().startNewActivity(RestaurantActivity::class.java)
                }
            }
            else {
                usernameFiend.error = "Invalid username"
                passwordFiend.error = "Invalid password"
            }
        })

        editTextPassword.addTextChangedListener {
            val username = editTextUsername.text.toString().trim()
            btnLogin.enable(username.isNotEmpty() && it.toString().isNotEmpty())
        }

        btnLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val encryptedPassword = Hashing.sha256().hashString(password, StandardCharsets.UTF_8).toString()
            progressBarLogin.visible(true)
            viewModel.getKorisnik(table="Korisnik", method = "select", username, encryptedPassword)
        }

        btnRegister.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

}


