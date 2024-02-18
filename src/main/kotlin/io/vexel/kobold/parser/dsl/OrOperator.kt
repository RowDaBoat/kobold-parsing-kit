package io.vexel.kobold.parser.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.OrderedChoice
import io.vexel.kobold.matchers.TerminalByContent

interface OrOperatorDSL {
    infix fun String.or(that: String): Matcher
    infix fun String.or(that: Token): Matcher
    infix fun String.or(that: Matcher): Matcher

    infix fun Token.or(that: String): Matcher
    infix fun Token.or(that: Token): Matcher
    infix fun Token.or(that: Matcher): Matcher

    infix fun Matcher.or(that: String): Matcher
    infix fun Matcher.or(that: Token): Matcher
    infix fun Matcher.or(that: Matcher): Matcher
}

class OrOperator : OrOperatorDSL {
    override infix fun String.or(that: String) =
        Token(this) or Token(that)

    override infix fun String.or(that: Token) =
        Token(this) or TerminalByContent(that)

    override infix fun String.or(that: Matcher) =
        Token(this) or that

    override infix fun Token.or(that: String) =
        TerminalByContent(this) or Token(that)

    override infix fun Token.or(that: Token) =
        TerminalByContent(this) or TerminalByContent(that)

    override infix fun Token.or(that: Matcher) =
        TerminalByContent(this) or that

    override infix fun Matcher.or(that: String) =
        this or Token(that)

    override infix fun Matcher.or(that: Token) =
        this or TerminalByContent(that)

    override infix fun Matcher.or(that: Matcher) =
        OrderedChoice(this, that)
}
