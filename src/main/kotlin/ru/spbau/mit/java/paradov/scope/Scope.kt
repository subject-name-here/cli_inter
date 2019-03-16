package ru.spbau.mit.java.paradov.scope

/**
 * Class that wraps variables map. It also has current directory as special variable.
 */
class Scope() {
    private val variables: HashMap<String, String> = HashMap()

    var currentDirectory = ""

    constructor(scope: Scope) : this() {
        variables.putAll(scope.variables)
        currentDirectory = scope.currentDirectory
    }

    fun set(key: String, value: String) {
        variables[key] = value
    }

    fun get(key: String) = variables[key]
}