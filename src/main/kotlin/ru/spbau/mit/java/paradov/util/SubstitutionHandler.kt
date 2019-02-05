package ru.spbau.mit.java.paradov.util

import ru.spbau.mit.java.paradov.scope.Scope

/**
 * Function that places all substitutions in the string, taking variables from given scope.
 */
fun substitute(scope: Scope, s: String): String {
    var newString = s
    val substitutionRegex = Regex("[$][A-Za-z0-9_]+")
    val substitutions = substitutionRegex.findAll(s).map { it.value }.sortedBy { word -> -word.length }
    for (ss in substitutions) {
        val variable = ss.substring(1)
        newString = newString.replace(ss, scope.get(variable) ?: "")
    }
    return newString
}