package com.example.smartwaiter.ui.guest.menuguestdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.database.db.SMDatabase
import com.example.smartwaiter.R
import com.example.smartwaiter.adapters.OrderDialogAdapter
import com.example.smartwaiter.repository.OrderRepository
import com.example.smartwaiter.ui.guest.menudetails.MenuDetailsViewModel
import com.example.smartwaiter.ui.guest.menudetails.MenuDetailsViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import hr.foi.air.webservice.model.Meal
import kotlinx.android.synthetic.main.dialog_complete_order.*

class MenuGuestDialogFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: MenuDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_complete_order, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val orderDialogAdapter = OrderDialogAdapter({meal: Meal -> deleteMealClicked(meal)})
        val repository = OrderRepository(SMDatabase(requireActivity()))
        val viewModelFactory = MenuDetailsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MenuDetailsViewModel::class.java)


        recyclerViewOrderMeal.apply {
            adapter = orderDialogAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getMeals().observe(viewLifecycleOwner, { meals ->
            orderDialogAdapter.submitList(meals)
            if (meals.isNullOrEmpty()){
                dismiss()
            }
        })
    }


    private fun deleteMealClicked(meal : Meal){
        viewModel.deleteMeal(meal)
        Snackbar.make(requireView(), "Meal successfully removed", Snackbar.LENGTH_LONG).apply {
            setAction("Undo"){
                viewModel.saveMeal(meal)
            }
            show()
        }
    }
}