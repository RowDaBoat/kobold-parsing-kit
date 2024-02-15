package kobold.lexer.dsl

import MatcherMemo
import kobold.matchers.*

interface ZeroOrMoreOperatorDSL {
    fun String.zeroOrMore(): Matcher
    fun Matcher.zeroOrMore(): Matcher
}

class ZeroOrMoreOperator(private val memo: MatcherMemo) : ZeroOrMoreOperatorDSL {
    override fun String.zeroOrMore() = zeroOrMoreOf(this.toMatcher())

    override fun Matcher.zeroOrMore() = zeroOrMoreOf(this)

    private fun zeroOrMoreOf(terminal: Matcher): NonTerminal {
        val recursion = NonTerminal(memo)
        recursion.from(OrderedChoice(Concatenation(terminal, recursion), Empty()))
        return recursion
    }
}
