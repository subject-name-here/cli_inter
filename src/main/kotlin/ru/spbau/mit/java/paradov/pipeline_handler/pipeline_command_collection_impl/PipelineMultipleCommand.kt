package ru.spbau.mit.java.paradov.pipeline_handler.pipeline_command_collection_impl

import ru.spbau.mit.java.paradov.parser.CommandCollection
import ru.spbau.mit.java.paradov.pipeline_handler.PipelineCommandCollection
import ru.spbau.mit.java.paradov.shell.MainShell
import ru.spbau.mit.java.paradov.shell.Shell
import ru.spbau.mit.java.paradov.shell.SubShell
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Collection of many commands in pipeline. It creates a subshell for every command and runs it as pipeless command.
 */
class PipelineMultipleCommand(
    private val tokensCollection: CommandCollection,
    private val mainShell: MainShell
) : PipelineCommandCollection() {
    override fun run(shell: Shell) {
        var inputStream = shell.inputStream
        var outputStream = ByteArrayOutputStream()
        val errorStream = shell.errorStream

        for (tokens in tokensCollection) {
            outputStream = ByteArrayOutputStream()
            val subShell = SubShell(shell, inputStream, outputStream, errorStream)
            try {
                PipelineSingleCommand(tokens, mainShell).run(subShell)
            } catch (e: Exception) {
                shell.printlnError(e.toString())
            }

            inputStream = ByteArrayInputStream(outputStream.toByteArray())
        }

        shell.print(outputStream.toString())
    }
}