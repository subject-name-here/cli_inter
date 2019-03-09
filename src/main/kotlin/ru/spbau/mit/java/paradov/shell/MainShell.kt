package ru.spbau.mit.java.paradov.shell

import ru.spbau.mit.java.paradov.commands.CommandCreator
import ru.spbau.mit.java.paradov.parser.StringParser
import ru.spbau.mit.java.paradov.pipeline_handler.PipelineCreator
import ru.spbau.mit.java.paradov.scope.Scope
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.OutputStream

/**
 * Shell implementation for the main shell that user sees and interacts with.
 */
class MainShell(
    private val pipelineCreator: PipelineCreator,
    val parser: StringParser,
    val commandCreator: CommandCreator
) : Shell() {
    override val inputStream = DataInputStream(System.`in`)
    override val outputStream = DataOutputStream(System.out)
    override val errorStream: OutputStream = DataOutputStream(System.out)
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
        scope.currentDirectory = System.getProperty("user.dir")

        do {
            print(scope.currentDirectory + " :) ")
            val line = readLine() ?: break

            try {
                val pipeline = pipelineCreator.stringToPipeline(this, line)
                pipeline.run(this)
            } catch (e: Exception) {
                printlnError(":( $e" )
            }
        } while (!isStopped)

    }

}