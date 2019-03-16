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
import java.io.File
import java.lang.StringBuilder

class CommandGrepTest {

    private val lineSep = System.lineSeparator()

    /** Temporary folder for files. */
    @Rule
    @JvmField
    val folder = TemporaryFolder()

    /*@Test
    fun testGrep1() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()

        val filename = "res1"
        val content = "abcdef$lineSep"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        every { shell.print(capture(slot)) } answers { sb.append(slot.captured) }
        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }*/
    // TODO: add tests


}