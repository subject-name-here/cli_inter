package ru.spbau.mit.java.paradov.util

import org.antlr.v4.runtime.Token
import ru.spbau.mit.java.paradov.commands.command_impl.*
import ru.spbau.mit.java.paradov.parser.PipelineParser
import ru.spbau.mit.java.paradov.scope.Scope
import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Turns tokens list to string list, but with substitutions where it's necessary and with strings without quotes.
 */
fun tokensToStringList(scope: Scope, tokens: List<Token>): List<String> {
    val commandAndArguments = ArrayList<String>()
    // Terrible crunch because I didn't invented good way to handle assignment.
    var isFirst = true
    for (token in tokens) {
        when (token.type) {
            PipelineParser.AnythingElse -> commandAndArguments.addAll(substitute(scope, token.text).splitBySpaces())
            PipelineParser.WeakString -> commandAndArguments.add(substitute(scope, token.text).dropQuotes())
            PipelineParser.StrongString -> commandAndArguments.add(token.text.dropQuotes())
            PipelineParser.Assignment -> commandAndArguments.add(if (isFirst) "assign ${token.text}" else token.text)
        }
        isFirst = false
    }
    return commandAndArguments
}

/**
 * Runs list of strings as command and its arguments.
 */
fun runCommandAndArguments(shell: Shell, commandAndArguments: List<String>) {
    val command = commandAndArguments[0]
    val arguments = commandAndArguments.drop(1)
    when (command) {
        "echo" -> CommandEcho(arguments, shell).run()
        "cat" -> CommandCat(arguments, shell).run()
        "wc" -> CommandWc(arguments, shell).run()
        "pwd" -> CommandPwd(arguments, shell).run()
        "exit" -> CommandExit(arguments, shell).run()
        "assign" -> CommandAssignment(arguments, shell).run()
        else -> {
            CommandProcess(command, arguments, shell).run()
        }
    }
}