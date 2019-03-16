package ru.spbau.mit.java.paradov

import ru.spbau.mit.java.paradov.commands.CommandCreatorImpl
import ru.spbau.mit.java.paradov.parser.StringParserImpl
import ru.spbau.mit.java.paradov.pipeline_handler.PipelineCreatorImpl
import ru.spbau.mit.java.paradov.shell.MainShell

/**
 * Main function that initializes and starts the main shell.
 */
fun main() {
    println("Hello there!")
    val mainShell = MainShell(PipelineCreatorImpl(), StringParserImpl(), CommandCreatorImpl())
    mainShell.start()
}