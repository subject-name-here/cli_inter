package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.FileNotFoundException

/**
 * Command that prints content of given file/files.
 */
class CommandCat(args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        if (args.isEmpty()) {
            val content = String(shell.inputStream.readAllBytes())
            shell.print(content)
        } else for (arg in args) {
            try {
                shell.print(shell.resolveDir(arg).toFile().readText())
            } catch (e: FileNotFoundException) {
                shell.printlnError("cat: file $arg not found")
            }
        }
    }
}