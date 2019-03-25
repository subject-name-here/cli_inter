package ru.spbau.mit.java.paradov

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.rules.TemporaryFolder
import ru.spbau.mit.java.paradov.scope.Scope
import ru.spbau.mit.java.paradov.shell.Shell
import java.io.File
import java.io.PrintWriter
import java.lang.StringBuilder
import java.nio.file.Paths

fun createTempFileWithContent(folder: TemporaryFolder, filename: String, content: String): File {
    val f = folder.newFile(filename)
    val pw = PrintWriter(f)
    pw.print(content)
    pw.flush()
    return f
}

fun shellMockk(sb: StringBuilder): Shell {
    val shell = mockk<Shell>()
    val slot = slot<String>()
    val scope = Scope()

    every { shell.print(capture(slot)) } answers { sb.append(slot.captured) }
    every { shell.println(capture(slot)) } answers { sb.appendln(slot.captured) }
    every { shell.printlnError(capture(slot)) } answers { sb.appendln(slot.captured) }
    every { shell.resolveDir(capture(slot)) } answers {
        scope.currentDirectory.resolve(Paths.get(slot.captured)).normalize()!!
    }
    return shell
}