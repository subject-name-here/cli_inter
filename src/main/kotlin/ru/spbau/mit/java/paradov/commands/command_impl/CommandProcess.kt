package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.File
import java.nio.file.Files
import java.util.concurrent.TimeUnit

/**
 * Command implementation that executes given command as external process.
 */
class CommandProcess(private val command: String, args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        val pb = ProcessBuilder(listOf(command) + args)
        pb.directory(shell.scope.currentDirectory.toFile())
        pb.redirectOutput(ProcessBuilder.Redirect.PIPE)
        pb.redirectInput(ProcessBuilder.Redirect.PIPE)

        val process = pb.start()
        process.waitFor(1, TimeUnit.MINUTES)
        process.inputStream.transferTo(shell.outputStream)
    }
}