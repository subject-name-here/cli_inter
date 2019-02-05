package ru.spbau.mit.java.paradov.pipeline_handler

import org.antlr.v4.runtime.Token
import org.junit.Test

import org.junit.Assert.*
import ru.spbau.mit.java.paradov.pipeline_handler.pipeline_command_collection_impl.*

class PipelineCreatorTest {

    @Test
    fun testStringToEmptyPipeline() {
        val s = ""
        assertTrue(stringToPipeline(s) is PipelineCommandEmptyCollection)
    }

    @Test
    fun testStringToSinglePipeline1() {
        val s = "aaaa bbb"
        val pipeline = stringToPipeline(s)
        assertTrue(pipeline is PipelineSingleCommand)
        assertEquals(tokenListToStringList((pipeline as PipelineSingleCommand).tokens), listOf(s))
    }

    @Test
    fun testStringToSinglePipeline2() {
        val s1 = "aaaa bbb"
        val s2 = "\"oh no it's a p|pe\""
        val s3 = " hehehe"
        val s = s1 + s2 + s3
        val pipeline = stringToPipeline(s)
        assertTrue(pipeline is PipelineSingleCommand)
        assertEquals(listOf(s1, s2, s3), tokenListToStringList((pipeline as PipelineSingleCommand).tokens))
    }

    @Test
    fun testStringToSinglePipeline3() {
        val s1 = "'|'"
        val s2 = "\"||||||||\""
        val s3 = "pipe"
        val s = s1 + s2 + s3
        val pipeline = stringToPipeline(s)
        assertTrue(pipeline is PipelineSingleCommand)
        assertEquals(listOf(s1, s2, s3), tokenListToStringList((pipeline as PipelineSingleCommand).tokens))
    }

    @Test
    fun testStringToMultiplePipeline1() {
        val s1 = "a1"
        val s2 = "a2"
        val s = listOf(s1, s2).joinToString("|")
        val pipeline = stringToPipeline(s)
        assertTrue(pipeline is PipelineMultipleCommand)
        val actual = CommandCollectionToStringListList((pipeline as PipelineMultipleCommand).tokensCollection)
        assertEquals(listOf(listOf(s1), listOf(s2)), actual)
    }

    @Test
    fun testStringToMultiplePipeline2() {
        val s11 = "now it will be "
        val s12 = "'comp|icated'"
        val s21 = "haha "
        val s31 = " other pipe"
        val s = listOf(s11 + s12, s21, s31).joinToString("|")
        val pipeline = stringToPipeline(s)
        assertTrue(pipeline is PipelineMultipleCommand)
        val actual = CommandCollectionToStringListList((pipeline as PipelineMultipleCommand).tokensCollection)
        assertEquals(listOf(listOf(s11, s12), listOf(s21), listOf(s31)), actual)
    }

    private fun tokenListToStringList(l: List<Token>) = l.map { it.text }

    private fun CommandCollectionToStringListList(p: CommandCollection) = p.map { tokenListToStringList(it) }
}