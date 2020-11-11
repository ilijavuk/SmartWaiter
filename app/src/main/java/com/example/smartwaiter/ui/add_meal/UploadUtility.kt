package com.example.smartwaiter.ui.add_meal


import android.app.Activity
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.File


class UploadUtility(activity: Activity) {

    /*var activity = activity;

    var serverURL: String = "https://vucko.net/sw-api/photo.php"
    var serverUploadDirectoryPath: String = "https://vucko.net/sw-api/uploads/"
    val client = OkHttpClient()

    fun uploadFile(sourceFilePath: String, uploadedFileName: String? = null) {
        uploadFile(File(sourceFilePath), uploadedFileName)
    }


    fun uploadFile(sourceFile: File, uploadedFileName: String? = null) {
        Thread {
            val mimeType = getMimeType(sourceFile);
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }

            val fileName: String = if (uploadedFileName == null)  sourceFile.name else uploadedFileName

            try {
                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("uploaded_file", fileName, sourceFile.asRequestBody(mimeType.toMediaTypeOrNull()))
                        .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()
                val response: Response = client.newCall(request).execute()
                Log.d("File upload", response.toString())
                if (response.isSuccessful) {

                    Log.d("File upload", "success, path: $serverUploadDirectoryPath$fileName")
                    //showToast("File uploaded successfully at $serverUploadDirectoryPath$fileName")
                } else {
                    Log.e("File upload", "failed")
                    //showToast("File uploading failed")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload", "failed")
                //showToast("File uploading failed")
            }

        }.start()

    }

    // url = file path or whatever suitable URL you want.
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        }
    }
*/

}