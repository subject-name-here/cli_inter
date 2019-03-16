package ru.spbau.mit.java.paradov.commands.command_impl

import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import ru.spbau.mit.java.paradov.createTempFileWithContent
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.ByteArrayOutputStream

class CommandProcessTest {
    private val lineSep = System.lineSeparator()

    /** Temporary folder for files. */
    @Rule
    @JvmField
    val folder = TemporaryFolder()

    @Test
    fun testProcess() {
        val shell = mockk<Shell>()
        val filename = "doc1"
        val content = "bbbb${lineSep}cccc${lineSep}aaaa$lineSep"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        val outputStream = ByteArrayOutputStream()
        every { shell.outputStream } returns outputStream
        every { shell.scope.currentDirectory } returns System.getProperty("user.dir")

        CommandProcess("sort", listOf(name), shell).run()
        assertEquals("aaaa${lineSep}bbbb${lineSep}cccc$lineSep", outputStream.toString())
    }
}