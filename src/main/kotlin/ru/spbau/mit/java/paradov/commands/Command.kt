package ru.spbau.mit.java.paradov.commands

import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Basic abstract class for command.
 */
abstract class Command(val args: List<String>, val shell: Shell) {
    /**
     * Function that executes command.
     */
    abstract fun run()
}