package com.vixiloc.vcashiermobile.domain.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)


enum class ValidationRule {
    Required,
    MinLength,
    MinValue,
    NotEmpty,
    NotNull
}