package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.ValidationResult

class ValidateNotEmpty {
    operator fun invoke(value: List<Any>): ValidationResult {
        return if (value.isNotEmpty()) {
            ValidationResult(true, null)
        } else {
            ValidationResult(false, "Field cannot be empty")
        }
    }
}