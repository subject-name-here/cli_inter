package ru.spbau.mit.java.paradov.commands.command_impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import ru.spbau.mit.java.paradov.shell.Shell
import java.lang.StringBuilder
import org.junit.rules.TemporaryFolder
import ru.spbau.mit.java.paradov.createTempFileWithContent


class CommandCatTest {
    private val lineSep = System.lineSeparator()

    /** Temporary folder for files. */
    @Rule @JvmField
    val folder = TemporaryFolder()

    @Test
    fun testCat1() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val content = "abcdef$lineSep"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        every { shell.print(capture(slot)) } answers { sb.append(slot.captured) }
        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }

    @Test
    fun testCat2() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res2"
        val content = "Great Grey Wolf Jumped Over Lazy Bachelor${lineSep}Quack!$lineSep"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        every { shell.print(capture(slot)) } answers { sb.append(slot.captured) }
        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }

    @Test
    fun testCat3() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename1 = "res1"
        val content1 = "abcdef$lineSep"
        val name1 = createTempFileWithContent(folder, filename1, content1).canonicalPath

        val filename2 = "res2"
        val content2 = "Great Grey Wolf Jumped Over Lazy Bachelor${lineSep}Quack!$lineSep"
        val name2 = createTempFileWithContent(folder, filename2, content2).canonicalPath

        every { shell.print(capture(slot)) } answers { sb.append(slot.captured) }
        CommandCat(listOf(name1, name2), shell).run()
        assertEquals(content1 + content2, sb.toString())
    }

    @Test
    fun testCat4() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "nonexistent doc"

        every { shell.printlnError(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandCat(listOf(filename), shell).run()
        val expected = "cat: file $filename not found$lineSep"
        assertEquals(expected, sb.toString())
    }

    @Test
    fun testCat5() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        
        val filename = "res5"
        val content = "ffffff$lineSep" +
                "ggggg$lineSep" +
                lineSep + lineSep + lineSep
        val name = createTempFileWithContent(folder, filename, content).canonicalPath
        
        every { shell.print(capture(slot)) } answers { sb.append(slot.captured) }
        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }

    @Test
    fun testCat6() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res6"
        val content = "this is bad"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        every { shell.print(capture(slot)) } answers { sb.append(slot.captured) }
        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }
}