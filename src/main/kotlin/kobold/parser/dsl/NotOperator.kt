package kobold.parser.dsl

import kobold.matchers.Token
import kobold.matchers.Matcher
import kobold.matchers.Not
import kobold.matchers.TerminalByContent

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
