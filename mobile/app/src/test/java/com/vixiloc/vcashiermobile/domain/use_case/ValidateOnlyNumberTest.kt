package com.vixiloc.vcashiermobile.domain.use_case

import org.junit.Assert.*
import org.junit.Test

class ValidateOnlyNumberTest {
    private val validateOnlyNumber = ValidateOnlyNumber()

    @Test
    fun `when input not only number`() {
        val input = "09ss"
        val result = validateOnlyNumber.invoke(input)
        assertEquals(false, result.successful)
        assertEquals(true, result.errorMessage?.isNotBlank())
        println("Test: when input not only number")
    }

    @Test
    fun `when input is only number`() {
        val input = "839"
        val result = validateOnlyNumber.invoke(input)
        assertEquals(true, result.successful)
        assertEquals(null, result.errorMessage)
        println("Test: when input not only number")
    }
}