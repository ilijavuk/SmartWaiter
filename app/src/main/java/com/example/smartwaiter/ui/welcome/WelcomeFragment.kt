package com.example.smartwaiter.ui.welcome

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.smartwaiter.R
import hr.foi.air.webservice.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


class WelcomeFragment : Fragment(R.layout.fragment_welcome) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            thread {


            }



        btnLogout.setOnClickListener {
            //val action = WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment()
            // findNavController().navigate(action)

            val args= mutableMapOf<String,String>("ime" to "Dominik", "prezime" to "Tomsic")


            runBlocking <Unit>{//glavni thread chilla dok networking ne odradi svoje

                var final="" //string u koji će se spremiti izvšena funkcija
                val job= GlobalScope.launch { //ovo mora bit tako, to napravi korutinu, unutra radite sranja s webservisom


                    var test=Webservice();
                    final=test.Korisnici()
                    Log.d("resp", test.Korisnici())
                    Log.d("bilokoji", test.APICall("select", "Korisnik", args))
                }
                job.join()  //ovo će spojiti taj thread s glavnim kad odradi što treba
                txtViewSuccess.text=final //i onda se može to iz networkinga koristiti na glavnom threadu!
            }

        }
    }
}