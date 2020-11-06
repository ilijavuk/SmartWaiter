package com.example.database

import android.util.Log
import hr.foi.air.webservice.Webservice
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserRepository {

    private val test= Webservice();

    /*fun loadUser(){
        runBlocking <Unit>{//glavni thread chilla dok networking ne odradi svoje

            var final="" //string u koji će se spremiti izvšena funkcija
            val job= GlobalScope.launch { //ovo mora bit tako, to napravi korutinu, unutra radite sranja s webservisom


                final=test.Korisnici()
                Log.d("resp", test.Korisnici())
                val args= mutableMapOf<String,String>("ime" to "Dominik", "prezime" to "Tomsic")
                Log.d("bilokoji", test.APICall("select", "Korisnik", args))
            }
            job.join()  //ovo će spojiti taj thread s glavnim kad odradi što treba
        }

    }*/

}