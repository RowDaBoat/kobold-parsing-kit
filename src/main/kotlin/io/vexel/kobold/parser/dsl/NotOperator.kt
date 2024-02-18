package io.vexel.kobold.parser.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.Not
import io.vexel.kobold.matchers.TerminalByContent

interface NotOperatorDSL {
    fun not(that: String): Matcher
    fun not(that: Token): Matcher
    fun not(that: Matcher): Matcher
}

class NotOperator : NotOperatorDSL {
    override fun not(that: String) =
        not(Token(that))

    override fun not(that: Token) =
        not(TerminalByContent(that))

    override fun not(that: Matcher) =
        Not(that)
}
