package ru.spbau.mit.java.paradov.scope

import java.nio.file.Paths

/**
 * Class that wraps variables map. It also has current directory as special variable.
 */
class Scope() {
    val variables: HashMap<String, String> = HashMap()

    var currentDirectory = Paths.get("")

    constructor(scope: Scope) : this() {
        variables.putAll(scope.variables)
        currentDirectory = scope.currentDirectory
    }

    fun set(key: String, value: String) {
        variables[key] = value
    }

    fun get(key: String) = variables[key]
}