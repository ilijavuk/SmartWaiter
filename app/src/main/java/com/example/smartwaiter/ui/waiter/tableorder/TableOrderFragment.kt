package com.example.smartwaiter.ui.waiter.tableorder

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.OrderRepository
import com.example.smartwaiter.repository.TableOrderRepository
import com.example.smartwaiter.ui.waiter.tablelist.*
import kotlinx.android.synthetic.main.fragment_table_list.*
import kotlinx.android.synthetic.main.fragment_table_order.*

class TableOrderFragment : Fragment(R.layout.fragment_table_order) {
    private val args: TableOrderFragmentArgs by navArgs()
    private lateinit var viewModel: TableOrderViewModel
    var time: Int = 5

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? =
        inflater.inflate(R.layout.fragment_table_order, container, false)

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = OrderRepository()
        val viewModelFactory = TableOrderViewModelFactory(repository)


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

                if(i <= 20){
                    button_confirm.setText(" Confrim: 5 min")
                    time = 5
                }else if(i > 20 && i <= 40) {
                    button_confirm.setText(" Confrim: 10 min")
                    time = 10

                }else if(i > 40 && i <= 60) {
                    button_confirm.setText(" Confrim: 15 min")
                    time = 15

                }else if(i > 60 && i <= 80) {
                    button_confirm.setText(" Confrim: 20 min")
                    time = 20

                }else{
                    button_confirm.setText(" Confrim: 25 min")
                    time = 25

                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })



        viewModel = ViewModelProvider(this, viewModelFactory).get(TableOrderViewModel::class.java)
        viewModel.getOrders("narudzba_stol", args.idStola.toString())

        viewModel.myResponse.observe(viewLifecycleOwner, {
            val response = it.body()
            if (response != null) {
                response.forEach {
                    table_grid_view_order.layoutManager = GridLayoutManager(activity, 1)
                    table_grid_view_order.adapter = TableOrderRecyclerAdapter(response, this)
                }
            }
        })

        button_confirm.setOnClickListener {

        }
    }
}