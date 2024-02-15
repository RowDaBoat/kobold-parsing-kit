package kobold.lexer.dsl

import MatcherMemo
import kobold.matchers.Concatenation
import kobold.matchers.Matcher
import kobold.matchers.NonTerminal
import kobold.matchers.OrderedChoice

interface OneOrMoreOperatorDSL {
    fun String.oneOrMore(): Matcher
    fun Matcher.oneOrMore(): Matcher
}

class OneOrMoreOperator(private val memo: MatcherMemo) : OneOrMoreOperatorDSL {
    override fun String.oneOrMore() = oneOrMoreOf(this.toMatcher())

    override fun Matcher.oneOrMore() = oneOrMoreOf(this)

    private fun oneOrMoreOf(terminal: Matcher): NonTerminal {
        val recursion = NonTerminal(memo)
        recursion.from(OrderedChoice(Concatenation(terminal, recursion), terminal))
        return recursion
    }
}

