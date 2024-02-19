package io.vexel.kobold.parser.dsl

import io.vexel.kobold.matchers.MatcherMemo
import io.vexel.kobold.Symbol
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.NonTerminal

interface NonTerminalOperatorDSL {
    fun nonTerminal(): NonTerminal
    fun nonTerminal(producer: (List<Symbol>) -> Symbol): NonTerminal
    infix fun NonTerminal.from(matcher: Matcher): Matcher
}

class NonTerminalOperators(private val memo : MatcherMemo) : NonTerminalOperatorDSL {
    override fun nonTerminal(): NonTerminal =
        NonTerminal(memo)

    override fun nonTerminal(producer: (List<Symbol>) -> Symbol): NonTerminal =
        NonTerminal(memo, producer)

    override infix fun NonTerminal.from(matcher: Matcher) =
        this.from(matcher)
}
