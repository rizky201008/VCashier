package com.vixiloc.vcashiermobile.domain.use_case

import org.junit.Assert.assertEquals
import org.junit.Test


class ValidateNotBlankTest {

    private val validateNotBlank = ValidateNotBlank()

    @Test
    fun `when input is blank then validation fails`() {
        val input = ""
        val result = validateNotBlank.invoke(input)
        assertEquals(false, result.successful)
        assertEquals(true, result.errorMessage?.isNotBlank())
        println("Test: when input is blank then validation fails done")
    }

    @Test
    fun `when input is not blank then validation succeeds`() {
        val input = "not blank"
        val result = validateNotBlank.invoke(input)
        assertEquals(true, result.successful)
        assertEquals(null, result.errorMessage)
        println("Test: when input is not blank then validation succeeds done")
    }
}