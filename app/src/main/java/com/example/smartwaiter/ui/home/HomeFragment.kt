package com.example.smartwaiter.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Repository
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository()
        val viewModelFactory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)


        btnLogin.setOnClickListener {

            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            viewModel.getKorisnik("Korisnik", "select", username, password)
            var loginTrue: Boolean = false

            viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                    loginTrue = true

                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                    loginTrue = false
                }
            })

            if (loginTrue) {
                val action = HomeFragmentDirections.actionHomeFragmentToWelcomeFragment()
                findNavController().navigate(action)
            }

        }
    }


}


