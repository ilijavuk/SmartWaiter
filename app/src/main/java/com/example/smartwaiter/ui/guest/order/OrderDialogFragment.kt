package com.example.smartwaiter.ui.guest.order

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.UserPreferences
import com.example.database.db.SMDatabase
import com.example.database.db.models.OrderedMeal
import com.example.smartwaiter.R
import com.example.smartwaiter.adapters.OrderDialogAdapter
import com.example.smartwaiter.repository.GameRepository
import com.example.smartwaiter.repository.OrderRepository
import com.example.smartwaiter.repository.RegisterRepository
import com.example.smartwaiter.ui.auth.register.RegisterViewModel
import com.example.smartwaiter.ui.auth.register.RegisterViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.dialog_complete_order.*

class OrderDialogFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: OrderViewModel
    private lateinit var viewModelGame: GameViewModel
    private lateinit var userPreferences: UserPreferences
    private var basketHandler: Boolean = false
    private var user = 0


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

        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                user = it.toInt()
                Toast.makeText(requireContext(), user.toString(), Toast.LENGTH_LONG).show()

            }
        })



        val orderDialogAdapter = OrderDialogAdapter { orderedMeal: OrderedMeal ->
            deleteOrderedMealClicked(
                orderedMeal
            )
        }
        val repository = OrderRepository(SMDatabase(requireActivity()), userPreferences)
        val repositoryGame = GameRepository()

        val viewModelFactoryGame = GameViewModelFactory(repositoryGame)
        viewModelGame = ViewModelProvider(this, viewModelFactoryGame).get(GameViewModel::class.java)

        val viewModelFactory = OrderViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(OrderViewModel::class.java)

        recyclerViewOrderMeal.apply {
            adapter = orderDialogAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getOrderedMeals().observe(viewLifecycleOwner, { orderedMeals ->
            if (orderedMeals.isNullOrEmpty()) {
                basketHandler = false
                dismiss()
            } else {
                basketHandler = true
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
                    viewModel.deleteAllFromOrder()
                    basketHandler = false
                    val action = OrderDialogFragmentDirections.actionMenuGuestDialogFragmentToWaitMealFragment()
                    findNavController().navigate(action)
                }

                is Resource.Loading -> {
                    Snackbar.make(requireView(), "Making Order...", Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {
                    Snackbar.make(requireView(), "Can't make Order", Snackbar.LENGTH_SHORT).show()
                }
            }

        })

        buttonCompleteOrder.setOnClickListener {
            var id_korisnik: Int = 0
            viewModel.getOrderedMeals().observe(viewLifecycleOwner, { orderedMeals ->
                orderedMeals!!.forEach {
                    id_korisnik = it.order.korisnik_id
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

            val xp = textViewFinalPrice.text.toString().split(".")
            val xpf = xp[0].takeLast(2)
            viewModelGame.setXp("razina", "update_xp", user, xpf.toInt())
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.saveOrderBucket(basketHandler)
        setFragmentResult(
            "basket_ui_request",
            bundleOf("basket_ui_result" to basketHandler)
        )
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