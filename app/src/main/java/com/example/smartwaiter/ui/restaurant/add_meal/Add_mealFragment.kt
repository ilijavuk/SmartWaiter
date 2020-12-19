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
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.AddRestaurantRepository
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.ui.restaurant.add_restaurant.AddRestaurantModelFactory
import com.example.smartwaiter.ui.restaurant.add_restaurant.AddRestaurantViewModel
import com.example.smartwaiter.util.handleApiError
import com.example.smartwaiter.util.visible
import hr.foi.air.webservice.UploadUtility
import hr.foi.air.webservice.Webservice
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_add_meal.*
import kotlinx.android.synthetic.main.fragment_edit_meal.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.*
import java.sql.Timestamp


class Add_mealFragment: Fragment(R.layout.fragment_add_meal) {
    private lateinit var viewModel: Add_mealViewModel
    private lateinit var ExistingTags: List<Tag>
    private lateinit var repository: Add_mealRepository
    private lateinit var viewModelFactory: Add_mealModelFactory
    private lateinit var newItemId: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        repository = Add_mealRepository()
        viewModelFactory = Add_mealModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(Add_mealViewModel::class.java)





        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    if (response != null) {
                        newItemId = response.value
                        Log.d("novi id", newItemId)
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        loadExistingTags()

                    }
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                    handleApiError(response) {  }
                    Toast.makeText(context, "Adding failed", Toast.LENGTH_SHORT).show()
                }
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
            var pathNaServeru ="https://smartwaiter.app/sw-api/uploads/"
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
                        var nastavak = file.absolutePath.substringAfterLast(".")
                        var imageName = lokal_id + name + timestampforname + "." + nastavak
                        pathNaServeru = pathNaServeru + imageName

                        UploadUtility(Activity()).uploadFile(file.absolutePath, imageName)
                    }
                }

                viewModel.insertMeal(table = "Stavka_jelovnika", method = "insert", name, price, description, pathNaServeru, requireArguments().getInt("restaurant_id").toString())

                textMealDescription.text=null
                textMealName.text=null
                textMealPrice.text=null
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

        }


    }




    private fun callExistingTags(){
        viewModel.getAllTags("Tag_stavke","select")
    }
    private fun loadExistingTags(){
        callExistingTags()
        viewModel.myResponse2.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    if (response != null) {
                        ExistingTags = response.value
                        Log.d("tagovi", ExistingTags.toString())
                        processNewTags()
                    }
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                    handleApiError(response) { callExistingTags() }
                }
            }
        })
    }



    private fun callInsertTag(newTag: String){
        viewModel.insertTag(table = "Tag_stavke", method = "insert", newTag)
    }
    private fun processNewTags(){
        var newTags:List<String> = tagsEditText.tags
        var allNewItemTags: MutableList<String> = mutableListOf<String>()
        for (newTag in newTags){
            var found = false
            for (existingTag in ExistingTags){
                if (existingTag.tag==newTag){
                    allNewItemTags.add(existingTag.id_tag)
                    found = true
                }
            }
            if(!found){

                callInsertTag(newTag)

                viewModel.myResponse3.observe(viewLifecycleOwner, { response ->
                    when (response) {
                        is Resource.Success -> {
                            if (response != null) {
                                if(!allNewItemTags.contains(response.value) ){
                                    allNewItemTags.add(response.value)
                                }
                                if(allNewItemTags.size == newTags.size){
                                    Log.d("tagovi",allNewItemTags.toString())
                                    bindTagsToItem(allNewItemTags)
                                }
                            }
                        }
                        is Resource.Loading -> {
                        }
                        is Resource.Failure -> {
                            handleApiError(response) { callInsertTag(newTag) }
                        }
                    }
                })
            }

        }


    }
    private fun callBindTag(stavka_id: String, tag_id: String){
        viewModel.bindTag(table = "Stavka_tag", method = "insert", stavka_id=stavka_id,tag_id=tag_id)
    }

    private fun bindTagsToItem(tagsToBind:List<String>){
        for (tag in tagsToBind){
            callBindTag(newItemId, tag)
            viewModel.myResponse3.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {

                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Failure -> {
                        handleApiError(response) { callBindTag(newItemId, tag) }
                    }
                }
            })
        }
    }
}


