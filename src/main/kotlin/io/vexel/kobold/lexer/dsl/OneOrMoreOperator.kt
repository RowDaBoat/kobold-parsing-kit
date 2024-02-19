package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.matchers.MatcherMemo
import io.vexel.kobold.matchers.Concatenation
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.NonTerminal
import io.vexel.kobold.matchers.OrderedChoice

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

