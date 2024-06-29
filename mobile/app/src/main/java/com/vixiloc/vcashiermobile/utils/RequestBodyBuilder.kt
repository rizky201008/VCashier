package com.vixiloc.vcashiermobile.utils

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object RequestBodyBuilder {
    fun createString(
        data: String
    ): RequestBody {
        return data.toRequestBody("text/plain".toMediaType())
    }
}