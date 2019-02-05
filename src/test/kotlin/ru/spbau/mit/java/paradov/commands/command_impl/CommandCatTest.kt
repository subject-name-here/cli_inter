package ru.spbau.mit.java.paradov.commands.command_impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Test

import org.junit.Assert.*
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.File
import java.lang.StringBuilder

class CommandCatTest {
    private val resDir = "src${File.separator}test${File.separator}resources${File.separator}"

    @Test
    fun testCat1() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc1"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandCat(listOf(filename), shell).run()
        assertEquals("abcdef\n", sb.toString())
    }

    @Test
    fun testCat2() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc2"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandCat(listOf(filename), shell).run()
        assertEquals("Great Grey Wolf Jumped Over Lazy Bachelor\nQuack!\n", sb.toString())
    }

    @Test
    fun testCat3() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename1 = resDir + "doc2"
        val filename2 = resDir + "doc1"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandCat(listOf(filename1, filename2), shell).run()
        assertEquals("Great Grey Wolf Jumped Over Lazy Bachelor\nQuack!\nabcdef\n", sb.toString())
    }

    @Test
    fun testCat4() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "what doc"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandCat(listOf(filename), shell).run()
        val expected = "cat: file $filename not found\n"
        assertEquals(expected, sb.toString())
    }
}