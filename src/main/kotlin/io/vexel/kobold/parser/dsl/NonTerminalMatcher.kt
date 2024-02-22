package io.vexel.kobold.parser.dsl

import io.vexel.kobold.matchers.MatcherMemo
import io.vexel.kobold.Symbol
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.NonTerminal

interface NonTerminalMatcherDSL {
    fun nonTerminal(): NonTerminal
    fun nonTerminal(producer: (List<Symbol>) -> Symbol): NonTerminal
    infix fun NonTerminal.from(matcher: Matcher): Matcher
}

class NonTerminalMatcher(private val memo : MatcherMemo) : NonTerminalMatcherDSL {
    override fun nonTerminal(): NonTerminal =
        NonTerminal(memo)

    override fun nonTerminal(producer: (List<Symbol>) -> Symbol): NonTerminal =
        NonTerminal(memo, producer)

    override infix fun NonTerminal.from(matcher: Matcher) =
        this.from(matcher)
}
