package ru.spbau.mit.java.paradov.commands.command_impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import ru.spbau.mit.java.paradov.createTempFileWithContent
import ru.spbau.mit.java.paradov.shell.Shell
import java.lang.StringBuilder

class CommandWcTest {
    private val lineSep = System.lineSeparator()

    /** Temporary folder for files. */
    @Rule
    @JvmField
    val folder = TemporaryFolder()

    @Test
    fun testWc1() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val content = "abcdef$lineSep"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandWc(listOf(name), shell).run()
        assertEquals("1 1 ${content.length}${CommandWc.splitter}$name$lineSep", sb.toString())
    }

    @Test
    fun testWc2() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res2"
        val content = "Great Grey Wolf Jumped Over Lazy Bachelor${lineSep}Quack!"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandWc(listOf(name), shell).run()
        assertEquals("1 8 ${content.length}${CommandWc.splitter}$name$lineSep", sb.toString())
    }

    @Test
    fun testWc3() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename1 = "res3.1"
        val content1 = "golden girl${lineSep}great gun${lineSep}grace$lineSep"
        val name1 = createTempFileWithContent(folder, filename1, content1).canonicalPath

        val filename2 = "res3.2"
        val content2 = "far few fall${lineSep}free${lineSep}press${lineSep}f"
        val name2 = createTempFileWithContent(folder, filename2, content2).canonicalPath

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandWc(listOf(name1, name2), shell).run()
        val expected = "3 5 ${content1.length}${CommandWc.splitter}$name1$lineSep" +
                "3 6 ${content2.length}${CommandWc.splitter}$name2$lineSep" +
                "Total: 6 11 ${content1.length + content2.length}$lineSep"
        assertEquals(expected, sb.toString())
    }

    @Test
    fun testWc4() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = "nonexistent doc"
        every { shell.printlnError(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandWc(listOf(filename), shell).run()
        val expected = "wc: file $filename not found$lineSep"
        assertEquals(expected, sb.toString())
    }
}