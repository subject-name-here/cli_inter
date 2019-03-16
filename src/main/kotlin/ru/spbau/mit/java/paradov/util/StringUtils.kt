package ru.spbau.mit.java.paradov.util

/**
 * Removes quotes (first and last symbol) from string wrapped in quotes.
 */
fun String.dropQuotes() = this.drop(1).dropLast(1)

/**
 * Usual split by spaces symbols except it doesn't leave empty strings.
 */
fun String.splitBySpaces() = this.split("\\s+".toRegex()).filterNot { s -> s.isEmpty() }