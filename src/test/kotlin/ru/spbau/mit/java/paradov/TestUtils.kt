package ru.spbau.mit.java.paradov

import org.junit.rules.TemporaryFolder
import java.io.File
import java.io.PrintWriter

fun createTempFileWithContent(folder: TemporaryFolder, filename: String, content: String): File {
    val f = folder.newFile(filename)
    val pw = PrintWriter(f)
    pw.print(content)
    pw.flush()
    return f
}