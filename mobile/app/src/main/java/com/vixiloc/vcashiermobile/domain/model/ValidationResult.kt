package com.vixiloc.vcashiermobile.domain.model

sealed class ValidationResult<T>(val error: T? = null, val message: String? = null) {
    class Required<T>(error: T, message: String?) : ValidationResult<T>(error, message)
    class MinLength<T>(error: T, message: String?) : ValidationResult<T>(error, message)
    class MinValue<T>(error: T, message: String?) : ValidationResult<T>(error, message)
}


enum class ValidationRule {
    Required,
    MinLength,
    MinValue
}