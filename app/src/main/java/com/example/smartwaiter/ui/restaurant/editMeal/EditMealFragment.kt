package com.example.smartwaiter.ui.restaurant.editMeal

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.smartwaiter.R
import com.example.smartwaiter.repository.Add_mealRepository
import com.example.smartwaiter.util.handleApiError
import com.example.smartwaiter.util.visible
//import com.example.smartwaiter.ui.restaurant.menu.MenuFragmentDirections
import hr.foi.air.webservice.UploadUtility
import hr.foi.air.webservice.model.Tag
import hr.foi.air.webservice.util.Resource
import kotlinx.android.synthetic.main.fragment_edit_meal.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class EditMealFragment: Fragment(R.layout.fragment_edit_meal) {

    private lateinit var viewModel: EditMealViewModel
    private val args: EditMealFragmentArgs by navArgs()
    private lateinit var mealId : String
    private lateinit var ExistingTags: List<Tag>



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {




        mealId = args.mealId

        load()
        loadBindedMealTags()
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarEditMeal.visible(false)
                    EditMealContent.visible(true)
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
                    EditMealContent.visible(false)
                }
                is Resource.Failure -> {
                    handleApiError(response) { load() }
                    EditMealContent.visible(false)
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
            loadExistingTags()
            RemoveOldTags()
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




            val action = EditMealFragmentDirections.actionEditMealFragment2ToMeniFragment()
            //findNavController().navigate(action)

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
        viewModel.insertTag(table = "Tag_stavke", method = "insert", tag=newTag)
    }
    private fun processNewTags(){
        var newTags:List<String> = tagsEditTextEdit.tags
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
                                    Log.d("tagovi",allNewItemTags.toString())
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
        if(allNewItemTags.size == newTags.size){
            Log.d("tagovi",allNewItemTags.toString())
            bindTagsToItem(allNewItemTags)
        }


    }
    private fun callBindTag(stavka_id: String, tag_id: String){
        viewModel.bindTag(table = "Stavka_tag", method = "insert", stavka_id=stavka_id,tag_id=tag_id)
    }

    private fun bindTagsToItem(tagsToBind:List<String>){
        for (tag in tagsToBind){
            Log.d("tagovi", tag)
            callBindTag(mealId, tag)
            viewModel.myResponse3.observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {

                    }
                    is Resource.Loading -> {
                    }
                    is Resource.Failure -> {
                        handleApiError(response) { callBindTag(mealId, tag) }
                    }
                }
            })
        }
    }
    private fun loadMealTags(){
        viewModel.tagsByMeal("tagsByMeal", mealId)
    }
    private fun loadBindedMealTags(){
        loadMealTags()
        viewModel.myResponse5.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    if (response != null) {
                        var oldTags  = mutableListOf<String>()
                        for(tag in response.value){

                            oldTags.add(tag.tag)
                            Log.d("tagovi-stari",tag.tag)
                        }
                        val OldTags: Array<String> = oldTags.toTypedArray()
                        tagsEditTextEdit.setTags(OldTags)


                    }
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                    handleApiError(response) { loadMealTags() }
                }
            }
        })
    }

    private fun RemoveTagsFromMeal(){
        viewModel.RemoveTagsFromMeal("RemoveTagsFromMeal", mealId)
    }
    private fun RemoveOldTags(){
        RemoveTagsFromMeal()
        viewModel.myResponse6.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Failure -> {
                    handleApiError(response) { callExistingTags() }
                }
            }
        })
    }
}