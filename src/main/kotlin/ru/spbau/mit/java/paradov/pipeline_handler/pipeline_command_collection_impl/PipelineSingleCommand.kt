package ru.spbau.mit.java.paradov.pipeline_handler.pipeline_command_collection_impl

import ru.spbau.mit.java.paradov.parser.CommandAbstraction
import ru.spbau.mit.java.paradov.pipeline_handler.PipelineCommandCollection
import ru.spbau.mit.java.paradov.shell.MainShell
import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Pipeline that contains only one command.
 */
class PipelineSingleCommand(
    private val tokens: CommandAbstraction,
    private val mainShell: MainShell
) : PipelineCommandCollection() {
    override fun run(shell: Shell) {
        val stringList = mainShell.parser.commandAbstractionToStringList(shell.scope, tokens)
        val command = mainShell.commandCreator.createCommandAndArguments(shell, stringList)
        command.run()
    }
}
