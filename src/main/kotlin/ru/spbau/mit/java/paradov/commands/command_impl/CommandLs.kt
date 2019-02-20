package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import java.nio.file.Files
import java.nio.file.Path

class CommandLs(args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        if (args.isEmpty()) {
            printDir(shell.scope.currentDirectory)
        } else {
            args.forEach {
                val dir = shell.resolveDir(it)
                if (Files.exists(dir)) {
                    shell.println("$it:")
                    printDir(dir)
                    shell.println("")
                } else {
                    shell.println("$it does not exists")
                }
            }
        }
    }

    private fun printDir(path: Path) {
        Files.list(path).forEach { shell.println(it.fileName.toString()) }
    }
}