package ru.spbau.mit.java.paradov.commands.command_impl

import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import ru.spbau.mit.java.paradov.createTempFileWithContent
import ru.spbau.mit.java.paradov.shellMockk


class CommandCatTest {
    private val lineSep = System.lineSeparator()

    /** Temporary folder for files. */
    @Rule @JvmField
    val folder = TemporaryFolder()

    @Test
    fun testCat1() {
        val sb = StringBuilder()
        val shell = shellMockk(sb)

        val filename = "res1"
        val content = "abcdef$lineSep"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }

    @Test
    fun testCat2() {
        val sb = StringBuilder()
        val shell = shellMockk(sb)

        val filename = "res2"
        val content = "Great Grey Wolf Jumped Over Lazy Bachelor${lineSep}Quack!$lineSep"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }

    @Test
    fun testCat3() {
        val sb = StringBuilder()
        val shell = shellMockk(sb)

        val filename1 = "res1"
        val content1 = "abcdef$lineSep"
        val name1 = createTempFileWithContent(folder, filename1, content1).canonicalPath

        val filename2 = "res2"
        val content2 = "Great Grey Wolf Jumped Over Lazy Bachelor${lineSep}Quack!$lineSep"
        val name2 = createTempFileWithContent(folder, filename2, content2).canonicalPath

        CommandCat(listOf(name1, name2), shell).run()
        assertEquals(content1 + content2, sb.toString())
    }

    @Test
    fun testCat4() {
        val sb = StringBuilder()
        val shell = shellMockk(sb)

        val filename = "nonexistent doc"

        CommandCat(listOf(filename), shell).run()
        val expected = "cat: file $filename not found$lineSep"
        assertEquals(expected, sb.toString())
    }

    @Test
    fun testCat5() {
        val sb = StringBuilder()
        val shell = shellMockk(sb)
        
        val filename = "res5"
        val content = "ffffff$lineSep" +
                "ggggg$lineSep" +
                lineSep + lineSep + lineSep
        val name = createTempFileWithContent(folder, filename, content).canonicalPath
        
        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }

    @Test
    fun testCat6() {
        val sb = StringBuilder()
        val shell = shellMockk(sb)

        val filename = "res6"
        val content = "this is bad"
        val name = createTempFileWithContent(folder, filename, content).canonicalPath

        CommandCat(listOf(name), shell).run()
        assertEquals(content, sb.toString())
    }
}