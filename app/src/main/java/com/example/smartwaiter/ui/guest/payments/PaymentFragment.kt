package com.example.smartwaiter.ui.guest.payments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.database.UserPreferences
import com.example.smartwaiter.R
import com.example.smartwaiter.util.enable
import com.google.android.material.snackbar.Snackbar
import com.stripe.android.*
import com.stripe.android.model.ConfirmPaymentIntentParams
import com.stripe.android.model.PaymentMethod
import com.stripe.android.view.BillingAddressFields
import hr.foi.air.webservice.RetrofitInstance
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.txtViewSuccess
import kotlinx.android.synthetic.main.fragment_payment.imgViewSuccess
import kotlinx.android.synthetic.main.fragment_waiting_meal.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private var paymentSession: PaymentSession? = null
    private lateinit var selectedPaymentMethod: PaymentMethod
    private lateinit var userPreferences: UserPreferences
    private var stripe: Stripe? = null
    private var customer = ""
    private var userCost = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreferences = UserPreferences(requireContext())


        buttonPayOrder.enable(false)
        userPreferences.customerID.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                customer = it
                Log.d("cost",it)
                CustomerSession.initCustomerSession(requireContext(), PaymentEphemeralKeyProvider(it))
                paymentSession = PaymentSession(this, paymentSessionConfig())
                paymentSession?.init(createPaymentSessionListener())

                buttonChosePayment.setOnClickListener {
                    paymentSession?.presentPaymentMethodSelection()
                }

                buttonPayOrder.setOnClickListener {
                    confirmPayment(selectedPaymentMethod.id!!)
                }

            }
        })

        userPreferences.totalCost.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                textViewPrice.text = "Price: " + String.format("%.2f",it.toDouble()) + " HRK"
                userCost = String.format("%.2f",it.toDouble()).replace(".","")
                Log.d("cost",userCost)
            }
        })

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        paymentSession?.handlePaymentData(requestCode, resultCode, data ?: Intent())
    }

    private fun confirmPayment(paymentMethodId: String){

        val call = RetrofitInstance.api.payMeal(userCost, customer)
        call.enqueue(object : Callback<Any?> {
            override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                if (response.isSuccessful && response.body() != null){
                    val clientSecret = response.body().toString()
                    stripe = Stripe(requireContext(), PaymentConfiguration.getInstance(requireActivity().applicationContext).publishableKey)
                    stripe?.confirmPayment(this@PaymentFragment, ConfirmPaymentIntentParams.createWithPaymentMethodId(paymentMethodId,clientSecret))
                    lifecycleScope.launch {
                        animations()
                        navigateAfterSuccess()
                    }
                    Log.d("placanje" ,"444" + response.body().toString())
                }
                else{
                    Snackbar.make(requireView(), "Choose Payment Method",Snackbar.LENGTH_LONG).show()
                    Log.d("placanje" ,"445" + response.body().toString())
                }
            }

            override fun onFailure(call: Call<Any?>, t: Throwable) {
                Log.d("placanje" ,"446")
            }
        })
    }

    private fun paymentSessionConfig(): PaymentSessionConfig {
        return PaymentSessionConfig.Builder()
            .setShippingInfoRequired(false)
            .setShippingMethodsRequired(false)
            .setPaymentMethodTypes(
                listOf(PaymentMethod.Type.Card)
            )
            .setBillingAddressFields(BillingAddressFields.None)
            .setShouldShowGooglePay(false)
            .build()
    }

    private fun createPaymentSessionListener(): PaymentSession.PaymentSessionListener {
        return object : PaymentSession.PaymentSessionListener {
            override fun onCommunicatingStateChanged(isCommunicating: Boolean) {
                if (isCommunicating) {
                    Log.d("placanje","111")
                } else {
                    Log.d("placanje","112")
                }
            }

            override fun onError(errorCode: Int, errorMessage: String) {
                Log.d("placanje","error 113")
            }

            override fun onPaymentSessionDataChanged(data: PaymentSessionData) {
                if (data.useGooglePay) {
                    // customer intends to pay with Google Pay
                } else {
                    data.paymentMethod?.let { paymentMethod ->
                        textViewPaymentDetails.text = "Card: ${paymentMethod.card?.brand}"
                        selectedPaymentMethod = paymentMethod
                        Log.d("placanje","222")
                    }
                }

                // Update your UI here with other data
                if (data.isPaymentReadyToCharge) {
                    Log.d("placanje","333")
                    buttonPayOrder.enable(true)
                }
            }
        }
    }

    private suspend fun animations(){
        buttonChosePayment.animate().alpha(0f).duration = 400L
        buttonPayOrder.animate().alpha(0f).duration = 400L

        textViewPrice.animate()
            .alpha(0f)
            .translationXBy(1200f)

        textViewPaymentDetails.animate()
            .alpha(0f)
            .translationXBy(-1200f)

        delay(  300)

        successBackground.animate().alpha(1f).duration = 600L
        successBackground.animate().rotationBy(720f).duration = 600L
        successBackground.animate().scaleXBy(900f).duration = 800L
        successBackground.animate().scaleYBy(900f).duration = 800L

        delay(500)

        imgViewSuccess.animate().alpha(1f).duration = 1000L
        txtViewSuccess.animate().alpha(1f).duration = 1000L

        delay(1500L)
    }

    private fun navigateAfterSuccess(){
        val action = PaymentFragmentDirections.actionPaymentFragmentToRateRestaurant(requireArguments().getString("lokal_id").toString())
        findNavController().navigate(action)
    }

}