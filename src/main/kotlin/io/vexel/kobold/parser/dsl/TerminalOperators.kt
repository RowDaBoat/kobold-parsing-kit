package io.vexel.kobold.parser.dsl

import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.TerminalByType

interface TerminalOperatorDSL {
    fun<T> terminal(type: Class<T>): Matcher
}

class TerminalOperators : TerminalOperatorDSL {
    override fun<T> terminal(type: Class<T>) =
        TerminalByType(type)
}
