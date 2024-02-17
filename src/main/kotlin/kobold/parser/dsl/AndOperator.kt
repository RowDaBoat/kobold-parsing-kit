package kobold.parser.dsl

import kobold.matchers.Token
import kobold.matchers.Concatenation
import kobold.matchers.Matcher
import kobold.matchers.Not
import kobold.matchers.TerminalByContent

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
