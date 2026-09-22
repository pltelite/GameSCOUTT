package com.gamescout.app

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationTest {

    @Test
    fun emailValidation_isCorrect() {
        val validEmail = "student@example.com"
        val invalidEmail = ""
        assertTrue(validEmail.contains("@"))
        assertFalse(invalidEmail.contains("@"))
    }

    @Test
    fun passwordValidation_isCorrect() {
        val validPassword = "password123"
        val invalidPassword = "123"
        assertTrue(validPassword.length >= 6)
        assertFalse(invalidPassword.length >= 6)
    }
}