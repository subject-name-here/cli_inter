package ru.spbau.mit.java.paradov.shell

import ru.spbau.mit.java.paradov.pipeline_handler.stringToPipeline
import ru.spbau.mit.java.paradov.scope.Scope
import java.io.DataInputStream
import java.io.DataOutputStream
import java.nio.file.Paths

/**
 * Shell implementation for the main shell that user sees and interacts with.
 */
class MainShell : Shell() {
    override val inputStream = DataInputStream(System.`in`)
    override val outputStream = DataOutputStream(System.out)
    override val scope: Scope = Scope()

    override var isStopped = false

    /**
     * Function that starts main shell. All work of main shell is endless cycle:
     * - read a line
     * - turn it into pipeline
     * - run a pipeline
     * - print errors, if there's any
     * - exit, if command said so
     */
    fun start() {
        scope.currentDirectory = Paths.get(System.getProperty("user.dir"))

        do {
            print(scope.currentDirectory + " :) ")
            val line = readLine() ?: break

            try {
                val pipeline = stringToPipeline(line)
                pipeline.run(this)
            } catch (e: Exception) {
                println(":( $e" )
            }
        } while (!isStopped)

    }

}