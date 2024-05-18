package com.vixiloc.vcashiermobile.commons

import android.content.Context
import android.net.Uri
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class FileConverter(private val context: Context) {
    fun uriToMultipartBody(uri: Uri?, name: String?): MultipartBody.Part? {
        val filesDir = context.filesDir
        val file = File(filesDir, "gambar.png")
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val outputStream = file.outputStream()
            inputStream?.copyTo(outputStream)
            val requestBody = file.asRequestBody()
            val part = MultipartBody.Part.createFormData(name ?: "image", file.name, requestBody)
            return part
        } ?: return null
    }
}