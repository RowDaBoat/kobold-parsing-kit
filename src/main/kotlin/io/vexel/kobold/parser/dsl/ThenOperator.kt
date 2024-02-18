package io.vexel.kobold.parser.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Concatenation
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.TerminalByContent

interface ThenOperatorDSL {
    infix fun String.then(that: String): Matcher
    infix fun String.then(that: Token): Matcher
    infix fun String.then(that: Matcher): Matcher

    infix fun Token.then(that: String): Matcher
    infix fun Token.then(that: Token): Matcher
    infix fun Token.then(that: Matcher): Matcher

    infix fun Matcher.then(that: String): Matcher
    infix fun Matcher.then(that: Token): Matcher
    infix fun Matcher.then(that: Matcher): Matcher
}

class ThenOperator : ThenOperatorDSL {
    override infix fun String.then(that: String) =
        Token(this) then Token(that)

    override infix fun String.then(that: Token) =
        Token(this) then TerminalByContent(that)

    override infix fun String.then(that: Matcher) =
        Token(this) then that

    override infix fun Token.then(that: String) =
        TerminalByContent(this) then Token(that)

    override infix fun Token.then(that: Token) =
        TerminalByContent(this) then TerminalByContent(that)

    override infix fun Token.then(that: Matcher) =
        TerminalByContent(this) then that

    override infix fun Matcher.then(that: String) =
        this then Token(that)

    override infix fun Matcher.then(that: Token) =
        this then TerminalByContent(that)

    override infix fun Matcher.then(that: Matcher) =
        Concatenation(this, that)

}
