package com.vixiloc.vcashiermobile.domain.use_case

import org.junit.Assert.*
import org.junit.Test

class ValidateNotEmptyTest {

        private val validateNotEmpty = ValidateNotEmpty()

        @Test
        fun `when input is empty then validation fails`() {
            val input = emptyList<Any>()
            val result = validateNotEmpty.invoke(input)
            assertEquals(false, result.successful)
            assertEquals(true, result.errorMessage?.isNotBlank())
            println("Test: when input is empty then validation fails done")
        }

        @Test
        fun `when input is not empty then validation succeeds`() {
            val input = listOf("not empty")
            val result = validateNotEmpty.invoke(input)
            assertEquals(true, result.successful)
            assertEquals(null, result.errorMessage)
            println("Test: when input is not empty then validation succeeds done")
        }
}