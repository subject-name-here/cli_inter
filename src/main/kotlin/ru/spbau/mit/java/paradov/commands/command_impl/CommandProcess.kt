package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell

/**
 * Command implementation that executes given command as external process.
 */
class CommandProcess(private val command: String, args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        val pb = ProcessBuilder(osHandler(command, args))
        pb.directory(shell.scope.currentDirectory.toFile())
        pb.redirectOutput(ProcessBuilder.Redirect.PIPE)
        pb.redirectInput(ProcessBuilder.Redirect.PIPE)
        pb.redirectError(ProcessBuilder.Redirect.PIPE)

        val process = pb.start()
        process.inputStream.transferTo(shell.outputStream)

        val code = process.waitFor()
        if (code != 0) {
            shell.printlnError("process: non-zero exit value: $code")
            process.errorStream.transferTo(shell.errorStream)
        }
    }

    private fun osHandler(command: String, args: List<String>): List<String> {
        return if (System.getProperty("os.name").startsWith("Windows")) {
            listOf("cmd", "/c", command) + args
        } else {
            listOf(command) + args
        }
    }
}