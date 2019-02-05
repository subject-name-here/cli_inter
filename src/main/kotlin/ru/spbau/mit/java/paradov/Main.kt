package ru.spbau.mit.java.paradov

import ru.spbau.mit.java.paradov.shell.MainShell

/**
 * Main function that initializes and starts the main shell.
 */
fun main() {
    println("Hello there!")
    val mainShell = MainShell()
    mainShell.start()
}