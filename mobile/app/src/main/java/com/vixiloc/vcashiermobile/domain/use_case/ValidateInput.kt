package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.ValidationResult
import com.vixiloc.vcashiermobile.domain.model.ValidationRule

class ValidateInput {
    operator fun invoke(
        input: String,
        validationRule: ValidationRule,
        requiredLength: Int? = null
    ): ValidationResult<Boolean> {
        return when (validationRule) {
            ValidationRule.Required -> {
                if (input.isEmpty()) {
                    ValidationResult.Required(true, "Err: Wajib diisi")
                } else {
                    ValidationResult.Required(false, null)
                }
            }

            ValidationRule.MinLength -> {
                requiredLength?.let {
                    if (input.length < it) {
                        ValidationResult.MinLength(true, "Err: Minimal $it karakter")
                    } else {
                        ValidationResult.MinLength(false, null)
                    }
                }
                    ?: throw IllegalArgumentException("requiredLength must be provided for MinLength validation rule")
            }

            ValidationRule.MinValue -> {
                requiredLength?.let {
                    if (input.toInt() < it) {
                        ValidationResult.MinValue(true, "Err: Minimal $it")
                    } else {
                        ValidationResult.MinValue(false, null)
                    }
                }
                    ?: throw IllegalArgumentException("requiredLength must be provided for MinValue validation rule")
            }
        }
    }
}