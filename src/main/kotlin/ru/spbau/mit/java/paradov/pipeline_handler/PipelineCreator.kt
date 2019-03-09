package ru.spbau.mit.java.paradov.pipeline_handler

import ru.spbau.mit.java.paradov.parser.CommandCollection
import ru.spbau.mit.java.paradov.pipeline_handler.pipeline_command_collection_impl.*
import ru.spbau.mit.java.paradov.shell.MainShell

/**
 * Interface for creator of pipeline.
 */
interface PipelineCreator {
    /**
     * Creates PipelineCommandCollection from string using mainShell.
     */
    fun stringToPipeline(mainShell: MainShell, line: String?): PipelineCommandCollection
}

class PipelineCreatorImpl : PipelineCreator {
    override fun stringToPipeline(mainShell: MainShell, line: String?): PipelineCommandCollection {
        val commandCollection: CommandCollection
        try {
            commandCollection = mainShell.parser.stringToCommandCollection(line)
        } catch(e: Exception) {
            throw Exception("Error while parsing line. Possible explanation: $e")
        }

        return when (commandCollection.size) {
            0 -> PipelineCommandEmptyCollection()
            1 -> PipelineSingleCommand(commandCollection[0], mainShell)
            else -> PipelineMultipleCommand(commandCollection, mainShell)
        }
    }
}
