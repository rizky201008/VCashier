package com.vixiloc.vcashiermobile.domain.use_case

import androidx.core.util.PatternsCompat
import com.vixiloc.vcashiermobile.domain.model.ValidationResult

class ValidateEmail {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(false, "Error: Data wajib diisi!")
        }
        if (!PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(false, "Error: Format email salah!")
        }
        return ValidationResult(true, null)
    }
}