package ru.spbau.mit.java.paradov.parser

import org.antlr.v4.runtime.*
import ru.spbau.mit.java.paradov.scope.Scope

/**
 * Type alias for command that is represented by collections of tokens.
 */
typealias CommandAbstraction = List<Token>

/**
 * Type alias for collection of commands abstractions.
 */
typealias CommandCollection = List<CommandAbstraction>

/**
 * Interface for parser. It parses in two stages: first it splits string by pipes and returns collection
 * of CommandAbstraction. Then it can parse every command abstraction into list of strings that
 * were recognized as command and argument.
 */
interface StringParser {
    /**
     * Splits string by pipes into collection of commands abstractions.
     */
    fun stringToCommandCollection(line: String?): CommandCollection

    /**
     * Turns command abstraction to list of strings. Takes care of substitutions.
     */
    fun commandAbstractionToStringList(scope: Scope, tokens: CommandAbstraction): List<String>
}

