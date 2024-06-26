package com.vixiloc.vcashiermobile.domain.use_case

import com.vixiloc.vcashiermobile.domain.model.ValidationResult
import com.vixiloc.vcashiermobile.domain.model.ValidationRule

class ValidateInput {
    operator fun invoke(
        input: Any?,
        validationRule: ValidationRule,
        requiredLength: Int? = null
    ): ValidationResult {
        return when (validationRule) {
            ValidationRule.Required -> {
                if (input is String && input.isEmpty()) {
                    ValidationResult(false, "Error: Data wajib diisi!")
                } else {
                    ValidationResult(true, null)
                }
            }

            ValidationRule.MinLength -> {
                requiredLength?.let {
                    if (input is String && input.length < it) {
                        ValidationResult(false, "Err: Minimal $it karakter")
                    } else {
                        ValidationResult(true, null)
                    }
                }
                    ?: throw IllegalArgumentException("requiredLength must be provided for MinLength validation rule")
            }

            ValidationRule.MinValue -> {
                requiredLength?.let {
                    if (input is String && input.toInt() < it) {
                        ValidationResult(false, "Err: Minimal $it")
                    } else {
                        ValidationResult(true, null)
                    }
                }
                    ?: throw IllegalArgumentException("requiredLength must be provided for MinValue validation rule")
            }

            ValidationRule.NotEmpty -> {
                if (input is List<*> && input.isNotEmpty()) {
                    ValidationResult(true, null)
                } else {
                    ValidationResult(false, "Silahkan pilih minimal 1 produk")
                }
            }

            ValidationRule.NotNull -> {
                if (input != null) {
                    ValidationResult(true, null)
                } else {
                    ValidationResult(false, "Silahkan isi semua data yang diperlukan")
                }
            }
        }
    }
}