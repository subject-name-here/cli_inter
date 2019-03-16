package ru.spbau.mit.java.paradov.commands

import ru.spbau.mit.java.paradov.commands.command_impl.*
import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Interface for command creator. It takes shell and list of command and arguments
 * and returns a Command implementation.
 */
interface CommandCreator {
    /**
     * Interprets list of strings as command and its arguments and returns Command implementation.
     */
    fun createCommandAndArguments(shell: Shell, commandAndArguments: List<String>): Command
}

class CommandCreatorImpl : CommandCreator {
    override fun createCommandAndArguments(shell: Shell, commandAndArguments: List<String>): Command {
        val command = commandAndArguments[0]
        val arguments = commandAndArguments.drop(1)
        return when (command) {
            "echo" -> CommandEcho(arguments, shell)
            "cat" -> CommandCat(arguments, shell)
            "wc" -> CommandWc(arguments, shell)
            "pwd" -> CommandPwd(arguments, shell)
            "grep" -> CommandGrep(arguments, shell)
            "exit" -> CommandExit(arguments, shell)
            else -> {
                if (command.contains("=")) {
                    CommandAssignment(command, arguments, shell)
                } else {
                    CommandProcess(command, arguments, shell)
                }
            }
        }
    }
}
