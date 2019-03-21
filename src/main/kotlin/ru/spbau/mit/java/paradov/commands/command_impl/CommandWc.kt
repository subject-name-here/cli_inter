package ru.spbau.mit.java.paradov.commands.command_impl

import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import ru.spbau.mit.java.paradov.util.splitBySpaces
import java.io.FileNotFoundException

/**
 * Command that counts words, lines (actually, not lines but linebreaks) and bytes in given file/files.
 */
class CommandWc(args: List<String>, shell: Shell) : Command(args, shell) {
    companion object {
        const val splitter = "    "
    }

    private class WordCountResult {
        var bytes: Int = 0
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

    private fun stringToWcResult(s: String): WordCountResult {
        val wcResult = WordCountResult()
        wcResult.lines = s.split(System.lineSeparator().toRegex()).size - 1
        wcResult.words = s.splitBySpaces().size
        wcResult.bytes = s.length
        return wcResult
    }

    override fun run() {
        if (args.isEmpty()) {
            val content = String(shell.inputStream.readAllBytes())
            val wcResult = stringToWcResult(content)
            shell.println("$wcResult")
        } else {
            val wcTotal = WordCountResult()
            for (arg in args) {
                try {
                    val content = shell.resolveDir(arg).toFile().readText()
                    val wcLocal = stringToWcResult(content)

                    shell.println("$wcLocal$splitter$arg")
                    wcTotal.add(wcLocal)
                } catch (e: FileNotFoundException) {
                    shell.printlnError("wc: file $arg not found")
                }
            }
            if (args.size > 1)
                shell.println("Total: $wcTotal")
        }
    }
}