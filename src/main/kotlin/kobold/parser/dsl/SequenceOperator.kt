package kobold.parser.dsl

import kobold.matchers.Token
import kobold.matchers.Concatenation
import kobold.matchers.Empty
import kobold.matchers.Matcher
import kobold.matchers.TerminalByContent

interface SequenceOperatorDSL {
    fun sequence(vararg that: String): Matcher
    fun sequence(vararg that: Token): Matcher
    fun sequence(vararg that: Matcher): Matcher
    fun sequence(that: List<Matcher>): Matcher
}

class SequenceOperator : SequenceOperatorDSL {
    override fun sequence(vararg that: String) = sequenceOf(that.map { TerminalByContent(Token(it)) }.toTypedArray())

    override fun sequence(vararg that: Token) = sequenceOf(that.map { TerminalByContent(it) }.toTypedArray())

    override fun sequence(vararg that: Matcher) = sequenceOf(that)

    override fun sequence(that: List<Matcher>): Matcher = sequenceOf(that.toTypedArray())

    private fun sequenceOf(that: Array<out Matcher>) = that.fold(Empty() as Matcher)
        { partial, matcher -> Concatenation(partial, matcher) }
}
