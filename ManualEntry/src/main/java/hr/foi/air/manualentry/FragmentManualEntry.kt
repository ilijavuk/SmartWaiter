package hr.foi.air.manualentry

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_manual_entry.*
import androidx.lifecycle.lifecycleScope
import com.example.database.HashCodeListener
import kotlinx.coroutines.launch


class FragmentManualEntry : Fragment(R.layout.fragment_manual_entry){


    private var ListenerCode: HashCodeListener? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnManualEntry.setOnClickListener {
            lifecycleScope.launch {
                val upisano = textEnterTableCode.text.toString()
                if(upisano.length==32) {

                    ListenerCode!!.onCodeObtained(upisano)
                }
                else{
                    Toast.makeText(context, "Šifra stola treba biti duljine 32 znaka", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is HashCodeListener) {
            ListenerCode = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        ListenerCode = null
    }

}

