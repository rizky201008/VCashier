package com.vixiloc.vcashiermobile.domain.use_case

import org.junit.Assert.assertEquals
import org.junit.Test

class ValidateValueGreaterTest {
    private val validateValueGreater = ValidateValueGreater()

    @Test
    fun `when input greater than minimum value return success`() {
        val input1 = "90000"
        val input2 = 10000
        val result = validateValueGreater.invoke(input1, input2)
        assertEquals(true, result.successful)
        println("Test: when input not only number")
    }

    @Test
    fun `when input is lower than minimum value return error`() {
        val input1 = "9000"
        val input2 = 10000
        val result = validateValueGreater.invoke(input1, input2)
        assertEquals(false, result.successful)
        println("Test: when input not only number")
    }
}