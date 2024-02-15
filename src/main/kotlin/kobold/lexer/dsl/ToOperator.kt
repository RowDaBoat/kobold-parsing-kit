package kobold.lexer.dsl

import kobold.matchers.*

interface ToOperatorDSL {
    infix fun Char.to(that: Char): Matcher
}

class ToOperator : ToOperatorDSL {
    override infix fun Char.to(that: Char) =
        (this..that)
            .map { TerminalByContent(Token(it)) }
            .fold(None() as Matcher) { result, current -> OrderedChoice(current, result) }
}
