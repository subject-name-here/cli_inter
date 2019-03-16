package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Command that executes assignment, that given as the first argument.
 */
class CommandAssignment(val command: String, args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        val operands = command.split("=", limit=2)
        shell.scope.set(operands[0], operands[1])
    }
}
