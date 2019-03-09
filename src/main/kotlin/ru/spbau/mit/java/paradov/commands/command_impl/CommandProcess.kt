package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.File

/**
 * Command implementation that executes given command as external process.
 */
class CommandProcess(private val command: String, args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        val pb = ProcessBuilder(listOf(command) + args)
        pb.directory(File(shell.scope.currentDirectory))
        pb.redirectOutput(ProcessBuilder.Redirect.PIPE)
        pb.redirectInput(ProcessBuilder.Redirect.PIPE)
        pb.redirectError(ProcessBuilder.Redirect.PIPE)

        val process = pb.start()
        process.inputStream.transferTo(shell.outputStream)

        process.waitFor()
        val code = process.exitValue()
        if (code != 0) {
            shell.printlnError("process: non-zero exit value: $code")
            process.errorStream.transferTo(shell.errorStream)
        }
    }
}