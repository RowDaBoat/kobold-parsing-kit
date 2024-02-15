package kobold.parser.dsl

import kobold.matchers.Token
import kobold.matchers.Matcher
import kobold.matchers.None
import kobold.matchers.OrderedChoice
import kobold.matchers.TerminalByContent

interface AnyOfOperatorDSL {
    fun anyOf(vararg that: String): Matcher
    fun anyOf(vararg that: Token): Matcher
    fun anyOf(vararg that: Matcher): Matcher
    fun anyOf(that: List<Matcher>): Matcher
}

class AnyOfOperator : AnyOfOperatorDSL {
    override fun anyOf(vararg that: String) = anyElementOf(that.map { TerminalByContent(Token(it)) }.toTypedArray())

    override fun anyOf(vararg that: Token) = anyElementOf(that.map { TerminalByContent(it) }.toTypedArray())

    override fun anyOf(vararg that: Matcher) = anyElementOf(that)

    override fun anyOf(that: List<Matcher>) = anyElementOf(that.toTypedArray())

    private fun anyElementOf(that: Array<out Matcher>) = that.fold(None() as Matcher) {
        partial, matcher ->
        OrderedChoice(partial, matcher)
    }
}
