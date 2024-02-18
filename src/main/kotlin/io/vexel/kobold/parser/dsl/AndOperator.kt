package io.vexel.kobold.parser.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Concatenation
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.Not
import io.vexel.kobold.matchers.TerminalByContent

interface AndOperatorDSL {
    infix fun String.and(that: String): Matcher
    infix fun String.and(that: Token): Matcher
    infix fun String.and(that: Matcher): Matcher

    infix fun Token.and(that: String): Matcher
    infix fun Token.and(that: Token): Matcher
    infix fun Token.and(that: Matcher): Matcher

    infix fun Matcher.and(that: String): Matcher
    infix fun Matcher.and(that: Token): Matcher
    infix fun Matcher.and(that: Matcher): Matcher
}

class AndOperator : AndOperatorDSL {
    override infix fun String.and(that: String) =
        Token(this) and Token(that)

    override infix fun String.and(that: Token) =
        Token(this) and TerminalByContent(that)

    override infix fun String.and(that: Matcher) =
        Token(this) and that

    override infix fun Token.and(that: String) =
        TerminalByContent(this) and Token(that)

    override infix fun Token.and(that: Token) =
        TerminalByContent(this) and TerminalByContent(that)

    override infix fun Token.and(that: Matcher) =
        TerminalByContent(this) and that

   override infix fun Matcher.and(that: String) =
        this and Token(that)

    override infix fun Matcher.and(that: Token) =
        this and TerminalByContent(that)

    override infix fun Matcher.and(that: Matcher) =
        Concatenation(Not(Not(that)), this)
}
