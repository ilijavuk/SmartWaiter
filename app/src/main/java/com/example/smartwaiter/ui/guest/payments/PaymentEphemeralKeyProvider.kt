package com.example.smartwaiter.ui.guest.payments


import android.util.Log
import com.google.gson.Gson
import com.stripe.android.EphemeralKeyProvider
import com.stripe.android.EphemeralKeyUpdateListener
import hr.foi.air.webservice.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentEphemeralKeyProvider(cutomerID: String) : EphemeralKeyProvider {

    private val customer = cutomerID

    override fun createEphemeralKey(
        apiVersion: String,
        keyUpdateListener: EphemeralKeyUpdateListener
    ) {
        val call = RetrofitInstance.api.getEphemeralKey(apiVersion, customer)
        call.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                if (response.isSuccessful && response.body() != null){
                    Log.d("key" ,response.body().toString())
                    keyUpdateListener.onKeyUpdate(Gson().toJson(response.body()))
                }
                else{
                    Log.d("key", "Greska")
                    keyUpdateListener.onKeyUpdateFailure(0,"")
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                keyUpdateListener.onKeyUpdateFailure(0, t?.message ?: "")
            }
        })
    }
}