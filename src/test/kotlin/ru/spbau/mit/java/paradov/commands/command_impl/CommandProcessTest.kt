package ru.spbau.mit.java.paradov.commands.command_impl

import io.mockk.every
import io.mockk.mockk
import org.junit.Test

import org.junit.Assert.*
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.ByteArrayOutputStream
import java.io.File

class CommandProcessTest {
    private val resDir = "src${File.separator}test${File.separator}resources${File.separator}"
    private val lineSep = System.lineSeparator()

    @Test
    fun testProcess() {
        val shell = mockk<Shell>()
        val filename = resDir + "doc4"

        val outputStream = ByteArrayOutputStream()
        every { shell.outputStream } returns outputStream
        every { shell.scope.currentDirectory} returns System.getProperty("user.dir")
        CommandProcess("sort", listOf(filename), shell).run()
        assertEquals("aaaa${lineSep}bbbb${lineSep}cccc${lineSep}", outputStream.toString())
    }
}