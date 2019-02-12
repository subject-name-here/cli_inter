package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import ru.spbau.mit.java.paradov.util.splitBySpaces
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * Command that counts words, lines and bytes in given file/files.
 */
class CommandWc(args: List<String>, shell: Shell) : Command(args, shell) {
    companion object {
        const val splitter = "    "
    }

    private class WordCountResult {
        var bytes: Long = 0
        var words: Int = 0
        var lines: Int = 0

        override fun toString(): String {
            return "$lines $words $bytes"
        }

        fun add(otherWcResult: WordCountResult) {
            bytes += otherWcResult.bytes
            words += otherWcResult.words
            lines += otherWcResult.lines
        }
    }

    override fun run() {
        if (args.isEmpty()) {
            val scanner = Scanner(shell.inputStream)
            val wcResult = WordCountResult()
            while (scanner.hasNext()) {
                val line = scanner.nextLine()
                wcResult.lines += 1
                wcResult.words += line.splitBySpaces().size
                wcResult.bytes += line.length
            }
            shell.println("$wcResult")
        } else {
            val wcTotal = WordCountResult()
            for (arg in args) {
                try {
                    val f = File(arg)
                    val wcLocal = WordCountResult()
                    wcLocal.bytes = f.length()
                    f.forEachLine {
                        wcLocal.lines += 1
                        wcLocal.words += it.splitBySpaces().size
                    }

                    shell.println("$wcLocal$splitter$arg")
                    wcTotal.add(wcLocal)
                } catch (e: FileNotFoundException) {
                    shell.println("wc: file $arg not found")
                }
            }
            if (args.size > 1)
                shell.println("Total: $wcTotal")
        }
    }
}