package com.vixiloc.vcashiermobile.domain.use_case

import org.junit.Assert.*
import org.junit.Test

class ValidateEmailTest {
    private val validateEmail = ValidateEmail()

    @Test
    fun `when email is blank`() {
        val input = ""
        val result = validateEmail.invoke(input)
        assertEquals(false, result.successful)
        println("Error : ${result.errorMessage}")
        println("Test: when email is blank done")
    }

    @Test
    fun `when email is invalid`() {
        val input = "email@blablabla"
        val result = validateEmail.invoke(input)
        assertEquals(false, result.successful)
        println("Error : ${result.errorMessage}")
        println("Test: when email is invalid done")
    }

    @Test
    fun `when email is valid`() {
        val input = "email@example.com"
        val result = validateEmail.invoke(input)
        assertEquals(true, result.successful)
        println("Test: when email is valid done")
    }
}