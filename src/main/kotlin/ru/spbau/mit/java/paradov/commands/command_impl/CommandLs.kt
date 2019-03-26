package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import java.nio.file.Files
import java.nio.file.Path

/**
 * Command that prints content of given folder/folders.
 */
class CommandLs(args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        if (args.isEmpty()) {
            printDir(shell.scope.currentDirectory)
        } else {
            args.forEach {
                val dir = shell.resolveDir(it)
                if (!Files.exists(dir)) {
                    shell.printlnError("ls: $it does not exists")
                } else if (!Files.isDirectory(dir)) {
                    shell.printlnError("ls: $it not a directory")
                } else {
                    shell.println("$it:")
                    printDir(dir)
                    shell.println("")
                }
            }
        }
    }

    private fun printDir(path: Path) {
        Files.list(path).forEach { shell.println(it.fileName.toString()) }
    }
}