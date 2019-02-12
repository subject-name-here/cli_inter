package ru.spbau.mit.java.paradov.commands.command_impl

import com.beust.jcommander.IParameterValidator
import com.beust.jcommander.Parameter
import ru.spbau.mit.java.paradov.commands.Command
import ru.spbau.mit.java.paradov.shell.Shell
import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException
import com.beust.jcommander.validators.PositiveInteger
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.HashSet


/**
 * Command that greps.
 */
class CommandGrep(args: List<String>, shell: Shell) : Command(args, shell) {
    private class ArgsParsed {
        @Parameter
        var targets: List<String> = arrayListOf()

        @Parameter(names = ["-w"], description = "Search by words")
        var wordsSearch = false

        @Parameter(names = ["-i"], description = "Case insensitive search")
        var caseInsensitive = false

        @Parameter(names = ["-A"], description = "Print given number of lines after ",
            validateWith = [ PositiveInteger::class ])
        var afterContext = 0
    }

    override fun run() {
        val argsParsed = ArgsParsed()
        JCommander(argsParsed).parse(*args.toTypedArray())

        if (argsParsed.targets.isEmpty()) {
            throw Exception("grep: expected regex!")
        }
        val regex = createRegexFromParsedArgs(argsParsed)
        val files = argsParsed.targets.drop(1)

        var currentContextLength = -1
        if (files.isEmpty()) {
            val scanner = Scanner(shell.inputStream)
            while (scanner.hasNext()) {
                val line = scanner.nextLine()
                currentContextLength = acceptLine(line, regex, currentContextLength, argsParsed.afterContext, "")
            }
        } else {
            for (file in files) {
                try {
                    File(file).forEachLine {
                        currentContextLength = acceptLine(
                            it,
                            regex,
                            currentContextLength,
                            argsParsed.afterContext,
                            if (files.size == 1) "" else file)
                    }
                } catch (e: FileNotFoundException) {
                    shell.println("grep: file $file not found")
                }
                currentContextLength = -1
            }
        }
    }

    private fun acceptLine(
        line: String,
        regex: Regex,
        currentContextLength: Int,
        maxContextLength: Int,
        prefix: String
    ): Int {
        val res = regex.find(line)
        return if (res == null) {
            if (currentContextLength > 0) {
                shell.println("${if (prefix.isEmpty()) "" else "$prefix - " }$line")
            } else if (currentContextLength == 0 && maxContextLength != 0) {
                shell.println("---")
            }
            currentContextLength - 1
        } else {
            shell.println("${if (prefix.isEmpty()) "" else "$prefix : " }$line")
            maxContextLength
        }
    }

    private fun createRegexFromParsedArgs(argsParsed: ArgsParsed): Regex {
        var regexString = argsParsed.targets[0]
        if (argsParsed.wordsSearch) {
            regexString = "(?<=^|\\s)$regexString(?=$|\\s+)"
        }

        val regexOptions = HashSet<RegexOption>()
        if (argsParsed.caseInsensitive) {
            regexOptions.add(RegexOption.IGNORE_CASE)
        }

        return regexString.toRegex(regexOptions)
    }
}