package ru.spbau.mit.java.paradov.pipeline_handler

import org.antlr.v4.runtime.*
import ru.spbau.mit.java.paradov.pipeline_handler.pipeline_command_collection_impl.*
import ru.spbau.mit.java.paradov.util.stringToCommandCollection

/**
 * Type alias for collection of commands. Every command is represented by collections of tokens.
 */
typealias CommandCollection = List<List<Token>>

/**
 * Util function to turn string to a pipeline command collection.
 */
fun stringToPipeline(line: String?): PipelineCommandCollection {
    val commandCollection: CommandCollection
    try {
        commandCollection = stringToCommandCollection(line)
    } catch(e: Exception) {
        throw Exception("Error while parsing line. Possible explanation: $e")
    }

    return when (commandCollection.size) {
        0 -> PipelineCommandEmptyCollection()
        1 -> PipelineSingleCommand(commandCollection[0])
        else -> PipelineMultipleCommand(commandCollection)
    }
}



