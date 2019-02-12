package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.util.*

/**
 * Command that prints content of given file/files.
 */
class CommandCat(args: List<String>, shell: Shell) : Command(args, shell) {
    override fun run() {
        if (args.isEmpty()) {
            val scanner = Scanner(shell.inputStream)
            while (scanner.hasNext()) {
                shell.println(scanner.nextLine())
            }
        } else for (arg in args) {
            try {
                File(arg).forEachLine { shell.println(it) }
            } catch (e: FileNotFoundException) {
                shell.println("cat: file $arg not found")
            }
        }
    }
}