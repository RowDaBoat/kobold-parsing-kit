package kobold.parser.dsl

import MatcherMemo
import kobold.matchers.*
import kobold.matchers.Token

interface ZeroOrMoreOperatorDSL {
    fun String.zeroOrMore(): Matcher
    fun Token.zeroOrMore(): Matcher
    fun Matcher.zeroOrMore(): Matcher
}

class ZeroOrMoreOperator(private val memo: MatcherMemo) : ZeroOrMoreOperatorDSL {
    override fun String.zeroOrMore() = zeroOrMoreOf(TerminalByContent(Token(this)))

    override fun Token.zeroOrMore() = zeroOrMoreOf(TerminalByContent(this))

    override fun Matcher.zeroOrMore() = zeroOrMoreOf(this)

    private fun zeroOrMoreOf(terminal: Matcher): NonTerminal {
        val recursion = NonTerminal(memo)
        recursion.from(OrderedChoice(Concatenation(terminal, recursion), Empty()))
        return recursion
    }
}
