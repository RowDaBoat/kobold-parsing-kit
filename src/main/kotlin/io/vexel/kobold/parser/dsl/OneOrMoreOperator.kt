package io.vexel.kobold.parser.dsl

import io.vexel.kobold.matchers.MatcherMemo
import io.vexel.kobold.Token
import io.vexel.kobold.matchers.*

interface OneOrMoreOperatorDSL {
    fun String.oneOrMore(): Matcher
    fun Token.oneOrMore(): Matcher
    fun Matcher.oneOrMore(): Matcher
}

class OneOrMoreOperator(private val memo: MatcherMemo) : OneOrMoreOperatorDSL {
    override fun String.oneOrMore() =
        oneOrMoreOf(TerminalByContent(Token(this)))

    override fun Token.oneOrMore() =
        oneOrMoreOf(TerminalByContent(this))

    override fun Matcher.oneOrMore() =
        oneOrMoreOf(this)

    private fun oneOrMoreOf(terminal: Matcher): NonTerminal {
        val recursion = NonTerminal(memo)
        recursion.from(OrderedChoice(Concatenation(terminal, recursion), terminal))
        return recursion
    }
}
