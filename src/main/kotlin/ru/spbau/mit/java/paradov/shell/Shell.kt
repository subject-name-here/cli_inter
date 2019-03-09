package ru.spbau.mit.java.paradov.shell

import ru.spbau.mit.java.paradov.scope.Scope
import java.io.InputStream
import java.io.OutputStream

/**
 * Shell abstract class that should wrap and handle scope and provide input and output.
 */
abstract class Shell {
    /**
     * Scope that is connected with this shell.
     */
    abstract val scope: Scope

    /**
     * Stream where all data is read from.
     */
    abstract val inputStream: InputStream

    /**
     * Stream where all data is written to.
     */
    abstract val outputStream: OutputStream

    /**
     * Stream where all errors are written to.
     */
    abstract val errorStream: OutputStream

    /**
     * Flag if shell is stopped or not.
     */
    abstract var isStopped: Boolean

    // Print function that writes given string to outputStream
    fun print(s: String) {
        outputStream.write(s.toByteArray())
    }

    // Print function that writes given string to outputStream, but with linebreak at the end.
    fun println(s: String) {
        print(s + System.lineSeparator())
    }

    // Function that prints error (with linebreak) in special stream for errors.
    fun printlnError(s: String) {
        errorStream.write((s + System.lineSeparator()).toByteArray())
    }
}