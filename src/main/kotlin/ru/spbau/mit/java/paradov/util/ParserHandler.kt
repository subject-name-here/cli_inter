package ru.spbau.mit.java.paradov.util

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.tree.TerminalNodeImpl
import ru.spbau.mit.java.paradov.parser.PipelineLexer
import ru.spbau.mit.java.paradov.parser.PipelineParser
import ru.spbau.mit.java.paradov.pipeline_handler.CommandCollection

/**
 * Class for throwing some exceptions that occurred during parser process.
 */
class PipelineHandlerErrorListener : BaseErrorListener() {
    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int, charPositionInLine: Int,
        msg: String?,
        e: RecognitionException?
    ) {
        throw Exception("Pipeline handler error: " + e.toString() + "; message: " + msg)
    }
}

/**
 * Function that parses string and turns it into collection of commands that are collections of tokens.
 */
fun stringToCommandCollection(line: String?): CommandCollection {
    val expLexer = PipelineLexer(CharStreams.fromString(line))
    val parser = PipelineParser(BufferedTokenStream(expLexer))

    expLexer.removeErrorListeners()
    parser.removeErrorListeners()
    expLexer.addErrorListener(PipelineHandlerErrorListener())
    parser.addErrorListener(PipelineHandlerErrorListener())

    val pipelineCommands = parser.pipeline().command()
    val commandCollection = ArrayList<ArrayList<Token>>()
    val currentCommandCollection = ArrayList<Token>()

    for (command in pipelineCommands) {
        for (thing in command.children) {
            currentCommandCollection.add((thing as TerminalNodeImpl).symbol)
        }
        commandCollection.add(ArrayList(currentCommandCollection))
        currentCommandCollection.clear()
    }

    return commandCollection
}