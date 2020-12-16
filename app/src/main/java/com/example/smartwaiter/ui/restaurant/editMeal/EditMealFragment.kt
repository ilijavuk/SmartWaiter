package com.example.smartwaiter.ui.restaurant.editMeal

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.auth.MainActivity
import com.example.smartwaiter.ui.guest.menu_guest.MealGuestListAdapter
import com.example.smartwaiter.util.handleApiError
import com.example.smartwaiter.util.visible
//import com.example.smartwaiter.ui.restaurant.menu.MenuFragmentDirections
import hr.foi.air.webservice.UploadUtility
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_add_meal.*
import kotlinx.android.synthetic.main.fragment_edit_meal.*
import kotlinx.android.synthetic.main.fragment_meni_guest.*
import kotlinx.android.synthetic.main.menu_list_item.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class EditMealFragment: Fragment(R.layout.fragment_edit_meal) {

    private lateinit var viewModel: EditMealViewModel
    private val args: EditMealFragmentArgs by navArgs()
    private lateinit var mealId : String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        mealId = args.mealId

        load()
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarEditMeal.visible(false)
                    EditMealLinearLayout.visible(true)
                    if (response != null) {
                        val odgovor = response.value
                        if (odgovor != null) {
                            for(m in odgovor){
                                textMealNameEdit.setText(m.naziv)
                                textMealNameEdit.setTag(m.id_stavka)
                                textMealDescriptionEdit.setText(m.opis)
                                textMealPriceEdit.setText(m.cijena)
                                textMealPriceEdit.setTag(m.lokal_id)
                                imageViewMealEdit.let {
                                    Glide.with(this)
                                        .load(m.slika_path)
                                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                                        .into(it)
                                }
                                textMealDescriptionEdit.setTag(m.slika_path)
                            }

                        }
                        imageTagHolder.setTag("0")
                    }
                }
                is Resource.Loading -> {
                    progressBarEditMeal.visible(true)
                    EditMealLinearLayout.visible(false)
                }
                is Resource.Failure -> {
                    handleApiError(response) { load() }
                    EditMealLinearLayout.visible(false)
                    progressBarEditMeal.visible(true)
                    Log.d("Response", response.toString())
                }
            }
        })


        btnEditItem.setOnClickListener{
            val repository = Add_mealRepository()
            val viewModelFactory = EditMealModelFactory(repository)
            var pathNaServeru ="https://smartwaiter.app/sw-api/uploads/"
            viewModel = ViewModelProvider(this, viewModelFactory).get(EditMealViewModel::class.java)

            val primaryKey:String = textMealNameEdit.getTag().toString()
            val nameOfMeal:String = textMealNameEdit.text.toString()
            val priceOfMeal:String = textMealPriceEdit.text.toString()
            val descritptionOfMeal:String = textMealDescriptionEdit.text.toString()
            var photoPathOfMeal:String = textMealDescriptionEdit.getTag().toString()


            if(imageTagHolder.getTag() == "0"){

            }
            else{
                val path: String = imageTagHolder.getTag().toString()

                var myUri: Uri = Uri.parse(path)


                Log.d("PATH1", path)
                val parcelFileDescriptor =
                    context?.contentResolver?.openFileDescriptor(myUri, "r", null)

                parcelFileDescriptor?.let {
                    val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                    val file =
                        File(context?.cacheDir, context?.contentResolver?.getFileName(myUri))
                    val outputStream = FileOutputStream(file)
                    inputStream.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                    val timestampforname = System.currentTimeMillis() / 1000
                    myUri = Uri.parse(file.absolutePath)
                    imageViewMealEdit.setImageURI(myUri)

                    val nastavak = file.absolutePath.substringAfterLast(".")
                    val lokal_id = textMealPriceEdit.getTag().toString()
                    val imageName =  lokal_id + nameOfMeal +timestampforname + "." + nastavak
                    pathNaServeru = pathNaServeru + imageName
                    photoPathOfMeal = pathNaServeru
                    UploadUtility(Activity()).uploadFile(file.absolutePath, imageName)
                }
            }

            viewModel.updateMeal(table = "Stavka_jelovnika", method = "update", mealId = primaryKey, mealName = nameOfMeal, mealPrice = priceOfMeal, mealDescription = descritptionOfMeal, mealPhotoPath = photoPathOfMeal)
            val action = EditMealFragmentDirections.actionEditMealFragment2ToMeniFragment(1)
            findNavController().navigate(action)

        }
        btnChoosePhotoEdit.setOnClickListener{
            openGalleryForImage()
        }


    }
    val REQUEST_CODE = 100

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }



    fun ContentResolver.getFileName(fileUri: Uri): String {

        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }

        return name
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            imageViewMealEdit.setImageURI(data?.data) // handle chosen image
            imageTagHolder.setTag(data?.data)
            var path=imageTagHolder.getTag().toString()
            Log.d("PATH2", imageTagHolder.getTag().toString())
        }
    }
    fun load(){
        val repository = Add_mealRepository()
        val viewModelFactory = EditMealModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditMealViewModel::class.java)
        viewModel.getMealById("Stavka_jelovnika","select", mealId)
    }
}