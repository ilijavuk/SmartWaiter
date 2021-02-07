package hr.foi.air.manualentry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.database.UserPreferences
import kotlinx.android.synthetic.main.fragment_manual_entry.*
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch


class FragmentManualEntry : Fragment(R.layout.fragment_manual_entry) {

    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: ManualEntryViewModel



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreferences = UserPreferences(requireContext())
        val repository = ManualRepository(userPreferences)
        val viewModelFactory = ManualEntryModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ManualEntryViewModel::class.java)


        btnManualEntry.setOnClickListener {
            lifecycleScope.launch {
                val upisano = textEnterTableCode.text.toString()
                if(upisano.length==32) {
                    viewModel.saveManualEntry(upisano)

                    userPreferences.manualEntry.asLiveData().observe(viewLifecycleOwner, {
                        it?.let {
                            Log.d("test", it)
                        }
                        getActivity()?.onBackPressed()
                    })
                }
                else{
                    Toast.makeText(context, "Šifra stola treba biti duljine 32 znaka", Toast.LENGTH_SHORT).show()
                }
            }

        }




    }

}

