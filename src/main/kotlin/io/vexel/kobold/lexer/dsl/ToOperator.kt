package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.None
import io.vexel.kobold.matchers.OrderedChoice
import io.vexel.kobold.matchers.TerminalByContent

interface ToOperatorDSL {
    infix fun Char.to(that: Char): Matcher
}

class ToOperator : ToOperatorDSL {
    override infix fun Char.to(that: Char) =
        (this..that)
            .map { TerminalByContent(Token(it)) }
            .fold(None() as Matcher) { result, current -> OrderedChoice(current, result) }
}
