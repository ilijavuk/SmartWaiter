package com.example.smartwaiter.ui.restaurant.restaurant_profil

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.example.database.UserPreferences
import com.example.smartwaiter.ui.auth.MainActivity
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.LvlRepository
import com.example.smartwaiter.repository.OrderRepository2
import com.example.smartwaiter.repository.RestaurantProfilRepository
import com.example.smartwaiter.ui.waiter.tablelist.TableOrderRecyclerAdapter
import com.example.smartwaiter.ui.waiter.tablelist.TableOrderViewModel
import com.example.smartwaiter.ui.waiter.tablelist.TableOrderViewModelFactory
import com.example.smartwaiter.util.startNewActivity
import kotlinx.android.synthetic.main.fragment_restaurant_profil.*
import kotlinx.android.synthetic.main.fragment_table_order.*
import kotlinx.coroutines.launch

class RestaurantProfilFragment  : Fragment(R.layout.fragment_restaurant_profil){
    private lateinit var userPreferences: UserPreferences
    var user = ""
    private lateinit var viewModel: RestaurantProfilViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreferences = UserPreferences(requireContext())
        val repositoryl = RestaurantProfilRepository(userPreferences)

        val repository = LvlRepository()
        val viewModelFactory = RestaurantProfilViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(RestaurantProfilViewModel::class.java)
        viewModel.getLvl("select_r")

        userPreferences.authToken.asLiveData().observe(viewLifecycleOwner, {
            it?.let {
                user = it
            }
        })

        viewModel.myResponse.observe(viewLifecycleOwner, {
            val response = it.body()
            if (response != null) {
                response.forEach {
                    if (it.id_korisnik == 14){
                        progress.progressMax = it.razina * 110f
                        progress.setProgressWithAnimation(it.iskustvo.toFloat())
                        name_tv.text = it.ime + " " + it.prezime
                        lvl_tv.text = "Lvl: " + it.razina
                    }
                }
            }
        })

        progress.apply {
            progressMax = 100f
            progressBarWidth = 7f
            progressBarColor = R.color.dark_red
            backgroundProgressBarColor = R.color.back_lightgery
        }
        progress.setProgressWithAnimation(70f,800)


        btnLogout.setOnClickListener {
            lifecycleScope.launch {
                repositoryl.logout()
                requireActivity().startNewActivity(MainActivity::class.java)
            }
        }

    }
}
