package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.ValidationResult

class ValidateValueGreater {
    operator fun invoke(value: String, minValue: Int): ValidationResult {
        if (value.isNotBlank() && value.toInt() < minValue) {
            return ValidationResult(false, "Nilai harus lebih dari atau sama dengan $minValue")
        }

        if (value.isBlank()) {
            return ValidationResult(false, "Nilai tidak boleh kosong")
        }

        return ValidationResult(true, null)
    }
}