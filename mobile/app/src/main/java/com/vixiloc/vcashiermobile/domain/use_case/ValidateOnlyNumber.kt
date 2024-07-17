package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.ValidationResult

class ValidateOnlyNumber {
    operator fun invoke(value: String): ValidationResult {
        if (!value.all { it.isDigit() }) {
            return ValidationResult(false, "Only numbers are allowed")
        }
        return ValidationResult(true, null)
    }
}