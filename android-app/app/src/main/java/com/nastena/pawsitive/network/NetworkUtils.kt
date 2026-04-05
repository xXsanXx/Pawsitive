package com.nastena.pawsitive.network

import android.content.ContentResolver
import android.net.Uri
import androidx.core.net.toUri
import com.google.gson.Gson
import com.nastena.pawsitive.utils.FileUtils
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.InputStream

object NetworkUtils {
    const val BASE_URL = "https://10.0.2.2:8080/"

    fun getAbsoluteFileUrl(localFileUrl: String): String {
        return FileUtils.getAbsoluteFileUrl(BASE_URL, localFileUrl);
    }

    fun photoUriToMultipart(
        uriString: String,
        multipartName: String,
        contentResolver: ContentResolver
    ): MultipartBody.Part {
        val uri: Uri = uriString.toUri()
        val file: File = getFileFromContentUri(uri, contentResolver)
        val requestFile: RequestBody = file.asRequestBody("image/*".toMediaType())
        return MultipartBody.Part.createFormData(multipartName, file.name, requestFile)
    }

    inline fun <reified T> dtoToRequestBody(request: T): RequestBody {
        val gson = Gson()
        val json: String = gson.toJson(request)
        return json.toRequestBody("application/json".toMediaType())
    }

    fun getFileFromContentUri(uri: Uri, contentResolver: ContentResolver): File {
        val inputStream: InputStream = contentResolver.openInputStream(uri)!!
        val tempFile = File.createTempFile("temp_image_", ".jpg")

        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        inputStream.close()
        return tempFile
    }
}