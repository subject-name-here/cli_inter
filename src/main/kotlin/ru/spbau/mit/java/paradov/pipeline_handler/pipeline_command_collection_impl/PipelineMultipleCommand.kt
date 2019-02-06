package ru.spbau.mit.java.paradov.pipeline_handler.pipeline_command_collection_impl

import ru.spbau.mit.java.paradov.pipeline_handler.CommandCollection
import ru.spbau.mit.java.paradov.pipeline_handler.PipelineCommandCollection
import ru.spbau.mit.java.paradov.shell.Shell
import ru.spbau.mit.java.paradov.shell.SubShell
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Collection of many commands in pipeline. It creates a subshell for every command and runs it as pipeless command.
 */
class PipelineMultipleCommand(val tokensCollection: CommandCollection) : PipelineCommandCollection() {
    override fun run(shell: Shell) {
        var inputStream = shell.inputStream
        var outputStream = ByteArrayOutputStream()

        for (tokens in tokensCollection) {
            outputStream = ByteArrayOutputStream()
            val subShell = SubShell(shell, inputStream, outputStream)
            try {
                PipelineSingleCommand(tokens).run(subShell)
            } catch (e: Exception) {
                shell.println(e.toString())
            }
            inputStream = ByteArrayInputStream(outputStream.toByteArray())
        }

        shell.print(outputStream.toString())
    }
}