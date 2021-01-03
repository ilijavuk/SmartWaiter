package com.example.smartwaiter.ui.waiter.tablelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.TableOrderRepository
import kotlinx.android.synthetic.main.fragment_table_list.*
import kotlinx.android.synthetic.main.table_list_item.*


class TableListFragment : Fragment(R.layout.fragment_table_list) {
    private lateinit var viewModel: TableListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? =
        inflater.inflate(R.layout.fragment_table_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = TableOrderRepository()
        val viewModelFactory = TableListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TableListViewModel::class.java)
        viewModel.getTable("Stol", "narudzba")

        viewModel.myResponse.observe(viewLifecycleOwner, {
            val response = it.body()
            if (response != null) {
                response.forEach {
                    table_grid_view.layoutManager = GridLayoutManager(activity, 2)
                    table_grid_view.adapter = TableRecyclerAdapter(response, this)
                }
            }
        })
    }

    fun callTable(id_stola: Int){
        val action = TableListFragmentDirections.actionTableListFragmentToTableOrderFragment(
            id_stola
        )
        findNavController().navigate(action)
    }
}