package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import java.nio.file.Files
import java.nio.file.Paths

class CommandCd(args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        if (args.size > 1) {
            shell.printlnError("cd: Too many arguments")
        }
        if (args.isEmpty()) {
            shell.scope.currentDirectory = Paths.get(System.getProperty("user.home"))
            return
        }
        val dir = shell.resolveDir(args[0])
        if (!Files.exists(dir)) {
            shell.printlnError("cd: $dir does not exists")
        } else if (!Files.isDirectory(dir)) {
            shell.printlnError("cd: $dir not a directory")
        } else {
            shell.scope.currentDirectory = dir
        }
    }
}