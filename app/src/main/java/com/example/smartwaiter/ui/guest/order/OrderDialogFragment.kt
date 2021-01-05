package com.example.smartwaiter.ui.guest.order

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.UserPreferences
import com.example.database.db.SMDatabase
import com.example.database.db.models.OrderedMeal
import com.example.smartwaiter.R
import com.example.smartwaiter.adapters.OrderDialogAdapter
import com.example.smartwaiter.repository.OrderRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.dialog_complete_order.*

class OrderDialogFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: OrderViewModel
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_complete_order, container, false)
        dialog?.setOnShowListener { dialog ->
            val bottomSheetBehavior = (dialog as BottomSheetDialog).behavior
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreferences = UserPreferences(requireContext())
        val orderDialogAdapter = OrderDialogAdapter { orderedMeal: OrderedMeal ->
            deleteOrderedMealClicked(
                orderedMeal
            )
        }
        val repository = OrderRepository(SMDatabase(requireActivity()), userPreferences)
        val viewModelFactory = OrderViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OrderViewModel::class.java)

        recyclerViewOrderMeal.apply {
            adapter = orderDialogAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getOrderedMeals().observe(viewLifecycleOwner, { orderedMeals ->
            if (orderedMeals.isNullOrEmpty()) {
                viewModel.saveOrderBucket(false)
                dismiss()
            } else {
                var sum = 0.0
                orderedMeals.forEach { orderedMeal ->
                    val mealPrice = orderedMeal.meal.cijena.toFloat() * orderedMeal.order.kolicina
                    sum += mealPrice
                }
                orderDialogAdapter.submitList(orderedMeals)
                textViewFinalPrice.text = getString(
                    R.string.final_price,
                    String.format("%.2f", sum)
                )
            }
        })

        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    Snackbar.make(requireView(), "OrderComplete", Snackbar.LENGTH_SHORT).show()
                    val action = OrderDialogFragmentDirections.actionMenuGuestDialogFragmentToWaitMealFragment()
                    findNavController().navigate(action)

                }

                is Resource.Loading -> {
                    Snackbar.make(requireView(), "Making Order...", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {
                    Snackbar.make(requireView(), "Can't make order", Snackbar.LENGTH_SHORT).show()
                }
            }

        })

        buttonCompleteOrder.setOnClickListener {
            viewModel.getOrderedMeals().observe(viewLifecycleOwner, { orderedMeals ->
                orderedMeals!!.forEach {
                    viewModel.makeOrder(
                        "Narudzba_novo",
                        "insert",
                        it.order.korisnik_id,
                        it.order.stol_id,
                        it.order.status,
                        it.order.vrijeme,
                        it.order.stavka_id,
                        it.order.kolicina
                    )
                }
            })
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val callback = targetFragment as? OrderDialogcallBack
        callback?.onOrderDialogcallBack()
    }

    interface OrderDialogcallBack{
        fun onOrderDialogcallBack()
    }

    private fun deleteOrderedMealClicked(orderedMeal: OrderedMeal) {
        viewModel.deleteOrderedMeal(orderedMeal)
        Snackbar.make(requireView(), "Meal successfully removed", Snackbar.LENGTH_LONG).apply {
            setAction("Undo") {
                viewModel.saveOrderedMeal(orderedMeal)
            }
            show()
        }
    }

}