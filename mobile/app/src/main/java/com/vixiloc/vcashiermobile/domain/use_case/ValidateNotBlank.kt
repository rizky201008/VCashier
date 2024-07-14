package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.ValidationResult

class ValidateNotBlank {
    operator fun invoke(
        input: String
    ): ValidationResult {
        if (input.isBlank()) {
            return ValidationResult(false, "Error: Data wajib diisi!")
        }
        return ValidationResult(true, null)
    }
}