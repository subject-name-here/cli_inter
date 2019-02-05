package ru.spbau.mit.java.paradov.commands.command_impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Test

import org.junit.Assert.*
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.File
import java.lang.StringBuilder

class CommandWcTest {
    private val resDir = "src${File.separator}test${File.separator}resources${File.separator}"

    @Test
    fun testWc1() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc1"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandWc(listOf(filename), shell).run()
        assertEquals("1 1 6${CommandWc.splitter}$filename\n", sb.toString())
    }

    @Test
    fun testWc2() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc2"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandWc(listOf(filename), shell).run()
        assertEquals("2 8 48${CommandWc.splitter}$filename\n", sb.toString())
    }

    @Test
    fun testWc3() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename1 = resDir + "doc3.1"
        val filename2 = resDir + "doc3.2"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandWc(listOf(filename1, filename2), shell).run()
        val expected = "3 5 27${CommandWc.splitter}$filename1\n" +
                "4 6 25${CommandWc.splitter}$filename2\n" +
                "Total: 7 11 52\n"
        assertEquals(expected, sb.toString())
    }

    @Test
    fun testWc4() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "what doc"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandWc(listOf(filename), shell).run()
        val expected = "File $filename not found!\n"
        assertEquals(expected, sb.toString())
    }
}