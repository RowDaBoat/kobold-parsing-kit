package io.vexel.kobold.parser.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Empty
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.OrderedChoice
import io.vexel.kobold.matchers.TerminalByContent

interface OptionalOperatorDSL {
    fun optional(that: String): Matcher
    fun optional(that: Token): Matcher
    fun optional(that: Matcher): Matcher
}

class OptionalOperator : OptionalOperatorDSL {
    override fun optional(that: String) =
        optional(Token(that))

    override fun optional(that: Token) =
        optional(TerminalByContent(that))

    override fun optional(that: Matcher) =
        OrderedChoice(that, Empty())
}
