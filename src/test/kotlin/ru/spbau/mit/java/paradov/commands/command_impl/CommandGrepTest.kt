package ru.spbau.mit.java.paradov.commands.command_impl

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Test

import org.junit.Assert.*
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.File
import java.lang.StringBuilder

private fun String.trimMarginUni() = this.trimMargin().replace("\n", System.lineSeparator())

class CommandGrepTest {

    private val resDir = "src${File.separator}test${File.separator}resources${File.separator}"

    @Test
    fun testGrep1() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc5"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("qq", filename), shell).run()
        assertEquals("""|there's qq
                        |another qq
                        |qq
                        |last qq
                        |""".trimMarginUni(), sb.toString())
    }

    @Test
    fun testGrep2() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc6"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("frog(gy)?", filename), shell).run()
        assertEquals("""|I've found frog
                        |it's name froggy
                        |froggygy
                        |""".trimMarginUni(), sb.toString())
    }

    @Test
    fun testGrep3() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc6"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-w", "frog(gy)?", filename), shell).run()
        assertEquals("""|I've found frog
                        |it's name froggy
                        |""".trimMarginUni(), sb.toString())
    }

    @Test
    fun testGrep4() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc6"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-i", "frog(gy)?", filename), shell).run()
        assertEquals("\r\n", System.getProperty("line.separator"))
        assertEquals("""|I've found frog
                        |it's name froggy
                        |froggygy
                        |FROG
                        |""".trimMarginUni(), sb.toString())
    }

    @Test
    fun testGrep5() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc6"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-w", "-i", "frog(gy)?", filename), shell).run()
        assertEquals("""|I've found frog
                        |it's name froggy
                        |FROG
                        |""".trimMarginUni(), sb.toString())
    }

    @Test
    fun testGrep6() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc6"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-A", "1", "frog(gy)?", filename), shell).run()
        assertEquals("""|I've found frog
                        |it's name froggy
                        |write in blog
                        |---
                        |froggygy
                        |fro g
                        |---
                        |""".trimMarginUni(), sb.toString())
    }

    @Test
    fun testGrep7() {
        val shell = mockk<Shell>()
        val sb = StringBuilder()
        val slot = slot<String>()
        val filename = resDir + "doc6"
        every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
        CommandGrep(listOf("-A", "1", "-i", "frog(gy)?", filename), shell).run()
        assertEquals("""|I've found frog
                        |it's name froggy
                        |write in blog
                        |---
                        |froggygy
                        |fro g
                        |FROG
                        |""".trimMarginUni(), sb.toString())
    }
}