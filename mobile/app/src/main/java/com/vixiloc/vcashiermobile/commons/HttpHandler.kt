package com.vixiloc.vcashiermobile.commons

import com.google.gson.Gson
import retrofit2.HttpException

class HttpHandler {
    fun handleHttpException(e: HttpException): String {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            val gson = Gson()
            val errorResponse: ErrorResponse? = errorBody?.let { gson.fromJson(it) }

            errorResponse?.message ?: e.localizedMessage ?: "An unexpected error occurred"
        } catch (ex: Exception) {
            "An unexpected error occurred"
        }
    }

    inline fun <reified T> Gson.fromJson(json: String): T? = try {
        fromJson(json, T::class.java)
    } catch (e: Exception) {
        null
    }

    private data class ErrorResponse(val message: String)
}