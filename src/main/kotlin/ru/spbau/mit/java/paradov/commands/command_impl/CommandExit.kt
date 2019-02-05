package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Command that terminates shell.
 */
class CommandExit(args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        shell.isStopped = true
    }
}