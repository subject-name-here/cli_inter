package ru.spbau.mit.java.paradov.parser

import org.antlr.v4.runtime.*
import org.antlr.v4.runtime.tree.TerminalNodeImpl
import ru.spbau.mit.java.paradov.scope.Scope
import ru.spbau.mit.java.paradov.util.dropQuotes
import ru.spbau.mit.java.paradov.util.substitute

/**
 * String parser implementation.
 */
class StringParserImpl : StringParser {
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

    override fun stringToCommandCollection(line: String?): CommandCollection {
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

    override fun commandAbstractionToStringList(scope: Scope, tokens: CommandAbstraction): List<String> {
        val commandAndArguments = ArrayList<String>()
        var endIndexOfLastCommand = -2
        for (token in tokens) {
            val currentCommand = when (token.type) {
                PipelineParser.VariableLike -> substitute(scope, token.text)
                PipelineParser.WeakString -> substitute(scope, token.text).dropQuotes()
                PipelineParser.StrongString -> token.text.dropQuotes()
                PipelineParser.Assignment -> {
                    val operands = token.text.split("=".toRegex(), 2)
                    val left = substitute(scope, operands[0])
                    val right = when {
                        operands[1][0] == '\'' -> operands[1].dropQuotes()
                        operands[1][0] == '"' -> substitute(scope, operands[1]).dropQuotes()
                        else -> substitute(scope, operands[1])
                    }
                    "$left=$right"
                }
                else -> ""
            }

            val lastCommandIndex = commandAndArguments.size - 1
            if (lastCommandIndex >= 0 && token.startIndex - endIndexOfLastCommand == 1) {
                commandAndArguments[lastCommandIndex] += currentCommand
            } else {
                commandAndArguments.add(currentCommand)
            }
            endIndexOfLastCommand = token.stopIndex
        }
        return commandAndArguments
    }

}