package com.example.smartwaiter.ui.waiter.tablelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.TableOrderRepository
import kotlinx.android.synthetic.main.fragment_table_list.*


class TableListFragment : Fragment(R.layout.fragment_table_list) {
    private lateinit var viewModel: TableListViewModel
    //private lateinit var final_response: MutableList<TableOrder>

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
        fillTableList("1")
        
        radio_group_table.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == checkbox_1.id){
                fillTableList("1")
            }
            else if(checkedId == checkbox_2.id){
                fillTableList("2")
            }
            else if(checkedId == checkbox_3.id){
                fillTableList("0")
            }

        }
    }


    fun callTable(id_stola: Int, rezerviran: Int) {
        val action = TableListFragmentDirections.actionTableListFragmentToTableOrderFragment(
            id_stola,
            rezerviran
        )
        findNavController().navigate(action)
    }


    fun fillTableList(s: String){
        viewModel.getTable("narudzba")
        viewModel.myResponse.observe(viewLifecycleOwner, {
            val response = it.body()

            if (response != null) {
                val final = response.filter { it.rezerviran.toString() == s }
                final.forEach {
                    table_grid_view.layoutManager = GridLayoutManager(activity, 2)
                    table_grid_view.adapter = TableRecyclerAdapter(final, this)
                }


            }



        })
    }
}