package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import ru.spbau.mit.java.paradov.util.splitBySpaces
import java.io.File
import java.io.FileNotFoundException

/**
 * Command that counts words, lines and bytes in given file/files.
 */
class CommandWc(args: List<String>, shell: Shell) : Command(args, shell) {
    companion object {
        const val splitter = "    "
    }

    override fun run() {
        if (args.isEmpty()) {
            // This part is mostly useless unless I'll figure out how to read from stdin after EOF.
            // throw Exception("wc: expected file as argument!")
            shell.println("0 0 0")
        } else {
            var words = 0
            var lines = 0
            var bytes: Long = 0
            for (arg in args) {
                try {
                    val f = File(arg)
                    val fileBytes = f.length()
                    var fileLines = 0
                    var fileWords = 0
                    f.forEachLine {
                        fileLines += 1
                        fileWords += it.splitBySpaces().size
                    }

                    shell.println("$fileLines $fileWords $fileBytes$splitter$arg")
                    words += fileWords
                    lines += fileLines
                    bytes += fileBytes
                } catch (e: FileNotFoundException) {
                    shell.println("wc: file $arg not found")
                }
            }
            if (args.size > 1)
                shell.println("Total: $lines $words $bytes")
        }
    }
}