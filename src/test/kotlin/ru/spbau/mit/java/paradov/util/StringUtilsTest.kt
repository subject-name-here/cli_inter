package ru.spbau.mit.java.paradov.util

import org.junit.Test

import org.junit.Assert.*

class StringUtilsTest {

    @Test
    fun testSplitBySpaces1() {
        val s = "a b c"
        val expected = listOf("a", "b", "c")
        assertEquals(expected, s.splitBySpaces())
    }

    @Test
    fun testSplitBySpaces2() {
        val s = "    a    ffdb    c    "
        val expected = listOf("a", "ffdb", "c")
        assertEquals(expected, s.splitBySpaces())
    }
}