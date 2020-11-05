package com.example.smartwaiter.ui.add_meal

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
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.smartwaiter.R
import kotlinx.android.synthetic.main.fragment_add_meal.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.*


class Add_mealFragment: Fragment(R.layout.fragment_add_meal) {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnChoosePhoto.setOnClickListener{
            openGalleryForImage()
        }

        btnAddItem.setOnClickListener{
            var name:String = textMealName.text.toString()
            var description:String = textMealDescription.text.toString()
            var price:String =textMealPrice.text.toString()
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


                var path: String = imageViewMeal.getTag().toString()

                var myUri:Uri = parse(path)


                //Log.d("PATH", path)
                val parcelFileDescriptor =
                    context?.contentResolver?.openFileDescriptor(myUri, "r", null)

                parcelFileDescriptor?.let {
                    val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                    val file = File(context?.cacheDir, context?.contentResolver?.getFileName(myUri))
                    val outputStream = FileOutputStream(file)
                    inputStream.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }
                    myUri = parse(file.absolutePath)
                    var pathFromUri = URIPathHelper().getPath(requireContext(),myUri)
                    imageViewMeal.setImageURI(myUri)
                    Log.d("PATH", file.absolutePath)

                    UploadUtility().uploadFile(file.absolutePath,"123.jpg")



                }





                /*
                var api: Webservice = Webservice()
                var send=""

                runBlocking {
                    val job = GlobalScope.launch {
                        var add=Webservice()
                        val args= mutableMapOf<String,String>("naziv" to name, "cijena" to price, "opis" to description, "slika_path" to "nema", "lokal_id" to "1")
                        add.APICall("insert","Stavka_jelovnika", args)
                    }
                }

                 */


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
            imageViewMeal.setTag(data?.data);
            var path=imageViewMeal.getTag().toString()
            Log.d("PATH", imageViewMeal.getTag().toString())



        }


    }

}