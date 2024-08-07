package com.vixiloc.vcashiermobile.domain.use_case

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidateMatchTest {

    private val validateNotMatch = ValidateMatch()

    @Test
    fun `when input is not match then validation fails`() {
        val input1 = "password"
        val input2 = "passwords"
        val result = validateNotMatch.invoke(input1, input2)
        assertEquals(false, result.successful)
        assertEquals(true, result.errorMessage?.isNotBlank())
        println("Test: when input is not match then validation fails")
    }

    @Test
    fun `when input is match then validation success`() {
        val input1 = "password"
        val input2 = "password"
        val result = validateNotMatch.invoke(input1, input2)
        assertEquals(true, result.successful)
        assertEquals(null, result.errorMessage)
        println("Test: when input is not empty then validation succeeds done")
    }
}