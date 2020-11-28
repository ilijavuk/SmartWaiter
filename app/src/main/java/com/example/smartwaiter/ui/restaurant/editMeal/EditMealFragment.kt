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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.auth.MainActivity
//import com.example.smartwaiter.ui.restaurant.menu.MenuFragmentDirections
import hr.foi.air.webservice.UploadUtility
import kotlinx.android.synthetic.main.fragment_add_meal.*
import kotlinx.android.synthetic.main.fragment_edit_meal.*
import kotlinx.android.synthetic.main.menu_list_item.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class EditMealFragment: Fragment(R.layout.fragment_edit_meal) {

    private lateinit var viewModel: EditMealViewModel
    private val args: EditMealFragmentArgs by navArgs()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        val mealId : String= args.mealId

        val repository = Add_mealRepository()
        val viewModelFactory = EditMealModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditMealViewModel::class.java)

        viewModel.getMealById("Stavka_jelovnika","select", mealId)

        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            val response = it.body()
            if (response != null) {
                for(m in response){
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
                    };
                    textMealDescriptionEdit.setTag(m.slika_path)
                }

            }
            imageTagHolder.setTag("0")
        })

        btnEditItem.setOnClickListener{
            val repository = Add_mealRepository()
            val viewModelFactory = EditMealModelFactory(repository)
            var pathNaServeru ="https://vucko.net/sw-api/uploads/"
            viewModel = ViewModelProvider(this, viewModelFactory).get(EditMealViewModel::class.java)

            var primaryKey:String = textMealNameEdit.getTag().toString()
            var nameOfMeal:String = textMealNameEdit.text.toString()
            var priceOfMeal:String = textMealPriceEdit.text.toString()
            var descritptionOfMeal:String = textMealDescriptionEdit.text.toString()
            var photoPathOfMeal:String = textMealDescriptionEdit.getTag().toString()


            if(imageTagHolder.getTag() == "0"){

            }
            else{
                var path: String = imageTagHolder.getTag().toString()

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
                    var timestampforname = System.currentTimeMillis() / 1000
                    myUri = Uri.parse(file.absolutePath)
                    imageViewMealEdit.setImageURI(myUri)

                    var nastavak = file.absolutePath.substringAfterLast(".")
                    var lokal_id = textMealPriceEdit.getTag().toString()
                    var imageName =  lokal_id + nameOfMeal +timestampforname + "." + nastavak
                    pathNaServeru = pathNaServeru + imageName
                    photoPathOfMeal = pathNaServeru
                    UploadUtility(Activity()).uploadFile(file.absolutePath, imageName)
                }
            }

            viewModel.updateMeal(table = "Stavka_jelovnika", method = "update", mealId = primaryKey, mealName = nameOfMeal, mealPrice = priceOfMeal, mealDescription = descritptionOfMeal, mealPhotoPath = photoPathOfMeal)
            val action = EditMealFragmentDirections.actionEditMealFragment2ToMeniFragment()
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
}