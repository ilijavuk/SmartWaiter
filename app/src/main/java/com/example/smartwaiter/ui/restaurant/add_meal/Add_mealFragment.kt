package com.example.smartwaiter.ui.restaurant.add_meal

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.net.Uri.*
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.OpenableColumns
import android.service.autofill.Validators.not
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.AddRestaurantRepository
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.restaurant.add_restaurant.AddRestaurantModelFactory
import com.example.smartwaiter.ui.restaurant.add_restaurant.AddRestaurantViewModel
import hr.foi.air.webservice.UploadUtility
import hr.foi.air.webservice.Webservice
import kotlinx.android.synthetic.main.fragment_add_meal.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.*
import java.sql.Timestamp


class Add_mealFragment: Fragment(R.layout.fragment_add_meal) {
    private lateinit var viewModel: Add_mealViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = Add_mealRepository()
        val viewModelFactory = Add_mealModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(Add_mealViewModel::class.java)
        viewModel.myResponse.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(context, "Adding failed", Toast.LENGTH_SHORT).show()

            }
        })



        btnChoosePhoto.setOnClickListener{
            openGalleryForImage()
        }
        btnAddItem.setOnClickListener{
            var name:String = textMealName.text.toString()
            var description:String = textMealDescription.text.toString()
            var price:String =textMealPrice.text.toString()
            var lokal_id = "1"
            var pathNaServeru ="https://vucko.net/sw-api/uploads/"
            var imageNotExists = true
            if(imageViewMeal.getTag() == null){

            }else{
                imageNotExists = false
            }
            if(name=="" || description =="" || price ==""){
                Toast.makeText(activity, getString(R.string.Toast_please_fill), Toast.LENGTH_LONG).show()
                val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }
            else{


                if(imageNotExists){
                    pathNaServeru=pathNaServeru+"default.jpg"
                }
                else {
                    var path: String = imageViewMeal.getTag().toString()

                    var myUri: Uri = parse(path)


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
                        myUri = parse(file.absolutePath)
                        imageViewMeal.setImageURI(myUri)
                        Log.d("PATH3", file.absolutePath)
                        var nastavak = file.absolutePath.substringAfterLast(".")
                        Log.d("PATH4", nastavak)
                        var imageName = lokal_id + name + timestampforname + "." + nastavak
                        pathNaServeru = pathNaServeru + imageName

                        UploadUtility(Activity()).uploadFile(file.absolutePath, imageName)
                    }
                }




                /*runBlocking {
                    val job = GlobalScope.launch {
                        var add= Webservice()
                        val args= mutableMapOf<String,String>("naziv" to name, "cijena" to price, "opis" to description, "slika_path" to pathNaServeru, "lokal_id" to "1")
                        add.APICall("insert","Stavka_jelovnika", args)
                    }
                }*/
                viewModel.insertMeal(table = "Stavka_jelovnika", method = "insert", name, price, description, pathNaServeru, "1")

                textMealDescription.text=null
                textMealName.text=null
                textMealPrice.text=null
                Toast.makeText(activity, getString(R.string.itemadded), Toast.LENGTH_LONG).show()
                imageViewMeal.setTag(null)
                imageViewMeal.setImageResource(R.drawable.meal_photo)
            }
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
            imageViewMeal.setImageURI(data?.data) // handle chosen image
            imageViewMeal.setTag(data?.data)
            var path=imageViewMeal.getTag().toString()
            Log.d("PATH2", imageViewMeal.getTag().toString())



        }


    }

}


