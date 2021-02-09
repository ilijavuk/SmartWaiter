package com.example.smartwaiter.ui.guest.HashHandler

import android.util.Log
import com.google.gson.Gson
import hr.foi.air.webservice.RetrofitInstance
import hr.foi.air.webservice.model.Stol
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HashHandler (hash: String) {

    private val Hash = hash
    private lateinit var stol:Stol

    fun hadnleHash(function: (Stol) -> (Unit)) {
        stol  = Stol("","","","","","")
        val call = RetrofitInstance.api.getTableHash("Stol", "select", Hash)
        runBlocking {
            val jobA = async {
                call.enqueue(object : Callback<List<Stol>> {
                    override fun onResponse(call: Call<List<Stol>>, response: Response<List<Stol>>) {
                        if (response.isSuccessful && response.body() != null){

                            stol = response.body()!![0]
                            Log.d("stolic2", stol.toString())
                            function(stol)
                        }
                        else{
                        }
                    }

                    override fun onFailure(call: Call<List<Stol>>, t: Throwable) {

                    }
                })
            }

            runBlocking{
                jobA.await()

            }
        }
    }
}