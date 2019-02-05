package ru.spbau.mit.java.paradov.pipeline_handler.pipeline_command_collection_impl

import org.antlr.v4.runtime.Token
import ru.spbau.mit.java.paradov.util.runCommandAndArguments
import ru.spbau.mit.java.paradov.util.tokensToStringList
import ru.spbau.mit.java.paradov.pipeline_handler.PipelineCommandCollection
import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Pipeline that contains only one command.
 */
class PipelineSingleCommand(val tokens: List<Token>) : PipelineCommandCollection() {
    override fun run(shell: Shell) {
        val stringList = tokensToStringList(shell.scope, tokens)
        runCommandAndArguments(shell, stringList)
    }
}


