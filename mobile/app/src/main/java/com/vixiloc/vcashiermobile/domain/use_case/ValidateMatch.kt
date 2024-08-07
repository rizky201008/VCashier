package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.ValidationResult

class ValidateMatch {
    operator fun invoke(
        input1: String,
        input2: String
    ): ValidationResult {
        if (input2 != input1) {
            return ValidationResult(false, "Error: Data harus sama!")
        }
        return ValidationResult(true, null)
    }
}