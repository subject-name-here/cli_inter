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

class CommandGrepTest {

    private val lineSep = System.lineSeparator()

    /** Temporary folder for files. */
    @Rule
    @JvmField
    val folder = TemporaryFolder()

    // Test matching of fragment.
    @Test
    fun testGrep1() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val content = "there's qq$lineSep" +
                "another qq$lineSep" +
                "no kuku$lineSep" +
                "qq$lineSep" +
                "no$lineSep" +
                "q q$lineSep" +
                "last qq$lineSep" +
                "the ned"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        val expected = "there's qq$lineSep" +
                "another qq$lineSep" +
                "qq$lineSep" +
                "last qq$lineSep"

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("qq", name), shell).run()
        assertEquals(expected, sb.toString())
    }

    private val commonContent = "I've found frog$lineSep" +
            "it's name froggy$lineSep" +
            "write in blog$lineSep" +
            "the day is foggy$lineSep" +
            "froog$lineSep" +
            "froggygy$lineSep" +
            "fro g$lineSep" +
            "FROG"

    // Test regex matching.
    @Test
    fun testGrep2() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val name = createTempFileWithContent(folder, filename, commonContent).canonicalPath

        val expected = "I've found frog$lineSep" +
                "it's name froggy$lineSep" +
                "froggygy$lineSep"

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("frog(gy)?", name), shell).run()
        assertEquals(expected, sb.toString())
    }

    // Test regex word matching (with flag -w).
    @Test
    fun testGrep3() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val name = createTempFileWithContent(folder, filename, commonContent).canonicalPath

        val expected = "I've found frog$lineSep" +
                "it's name froggy$lineSep"

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-w", "frog(gy)?", name), shell).run()
        assertEquals(expected, sb.toString())
    }

    // Test regex case insensitive matching (with flag -i).
    @Test
    fun testGrep4() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val name = createTempFileWithContent(folder, filename, commonContent).canonicalPath

        val expected = "I've found frog$lineSep" +
                "it's name froggy$lineSep" +
                "froggygy$lineSep" +
                "FROG$lineSep"

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-i", "frog(gy)?", name), shell).run()
        assertEquals(expected, sb.toString())
    }

    // Test regex word case insensitive matching (with flags -i and -w).
    @Test
    fun testGrep5() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val name = createTempFileWithContent(folder, filename, commonContent).canonicalPath

        val expected = "I've found frog$lineSep" +
                "it's name froggy$lineSep" +
                "FROG$lineSep"

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-w", "-i", "frog(gy)?", name), shell).run()
        assertEquals(expected, sb.toString())
    }

    // Test regex matching with after context of length 1 (flag "-A 1").
    @Test
    fun testGrep6() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val name = createTempFileWithContent(folder, filename, commonContent).canonicalPath

        val expected = "I've found frog$lineSep" +
                "it's name froggy$lineSep" +
                "write in blog$lineSep" +
                "---$lineSep" +
                "froggygy$lineSep" +
                "fro g$lineSep" +
                "---$lineSep"

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-A", "1", "frog(gy)?", name), shell).run()
        assertEquals(expected, sb.toString())
    }

    // Test regex case insensitive matching with after context of length 1 (flags "i" and "-A 1").
    @Test
    fun testGrep7() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val name = createTempFileWithContent(folder, filename, commonContent).canonicalPath

        val expected = "I've found frog$lineSep" +
                "it's name froggy$lineSep" +
                "write in blog$lineSep" +
                "---$lineSep" +
                "froggygy$lineSep" +
                "fro g$lineSep" +
                "FROG$lineSep"

        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-A", "1", "-i", "frog(gy)?", name), shell).run()
        assertEquals(expected, sb.toString())
    }

    // Test invalid key.
    @Test
    fun testGrep8() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val name = createTempFileWithContent(folder, filename, commonContent).canonicalPath

        val expectedError = "grep: unknown key -k$lineSep"

        every { shell.printlnError(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-k", "frog(gy)?", name), shell).run()
        assertEquals(expectedError, sb.toString())
    }

    // Test invalid keys.
    @Test
    fun testGrep9() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val name = createTempFileWithContent(folder, filename, commonContent).canonicalPath

        val expectedError = "grep: unknown keys -k, -l$lineSep"

        every { shell.printlnError(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-k", "-l", "frog(gy)?", name), shell).run()
        assertEquals(expectedError, sb.toString())
    }

}