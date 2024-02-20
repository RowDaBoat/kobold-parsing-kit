package io.vexel.kobold.parser.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.TerminalByContent
import io.vexel.kobold.matchers.TerminalByType

interface TerminalOperatorsDSL {
    fun<T: Token> terminal(type: Class<T>): Matcher
    fun terminal(token: Token): Matcher
}

class TerminalOperators : TerminalOperatorsDSL {
    override fun<T: Token> terminal(type: Class<T>) =
        TerminalByType(type)

    override fun terminal(token: Token) =
        TerminalByContent(token)
}
