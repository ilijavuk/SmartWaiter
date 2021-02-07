package com.example.smartwaiter.ui.guest.guestwaiting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartwaiter.R
import kotlinx.android.synthetic.main.fragment_waiting_meal.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaitMealFragment : Fragment(R.layout.fragment_waiting_meal) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonMakePayment.setOnClickListener {
            val action = WaitMealFragmentDirections.actionWaitMealFragmentToPaymentFragment()
            findNavController().navigate(action)
        }

        buttonCashPay.setOnClickListener {
            lifecycleScope.launch {
                animations()
                navigateAfterSuccess()
            }

        }
    }

    private suspend fun animations(){
        buttonCashPay.animate().alpha(0f).duration = 400L
        buttonMakePayment.animate().alpha(0f).duration = 400L
        textViewPrepareMeal.animate()
            .alpha(0f)
            .translationXBy(1200f)
            .duration = 400L
        lottieAnimationView.animate()
            .alpha(0f)
            .translationXBy(-1200f)
            .duration = 400L

        delay(  300)

        success_background.animate().alpha(1f).duration = 600L
        success_background.animate().rotationBy(720f).duration = 600L
        success_background.animate().scaleXBy(900f).duration = 800L
        success_background.animate().scaleYBy(900f).duration = 800L

        delay(500)

        imgViewSuccess.animate().alpha(1f).duration = 1000L
        txtViewSuccess.animate().alpha(1f).duration = 1000L

        delay(1500L)

    }

    private fun navigateAfterSuccess(){
        val action = WaitMealFragmentDirections.actionWaitMealFragmentToMenuGuestFragment()
        findNavController().navigate(action)
    }
}