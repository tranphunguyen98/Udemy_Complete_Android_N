package com.example.ntran.countdownegg

import org.junit.Test

import org.junit.Assert.*

class TestDemoUnitTest {
    @Test
    fun add_isCorrect() {
        assertEquals(4,TestDemo().add(2,2))
    }

    @Test
    fun add_isUnCorrect() {
        assertEquals(4,TestDemo().add(3,2))
    }
}