package ru.spbau.mit.java.paradov.shell

import ru.spbau.mit.java.paradov.scope.Scope
import java.io.InputStream
import java.io.OutputStream

/**
 * Shell implementation that is not launchable, but handles creation of scope copy.
 * It has only one purpose: to be used by only one command in pipeline without any effect on main shell.
 */
class SubShell(shell: Shell,
               override val inputStream: InputStream,
               override val outputStream: OutputStream,
               override val errorStream: OutputStream) : Shell() {
    override val scope = Scope(shell.scope)

    override var isStopped = false
}