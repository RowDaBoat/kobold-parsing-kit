package kobold.parser.dsl

import MatcherMemo
import kobold.matchers.Symbol
import kobold.matchers.Matcher
import kobold.matchers.NonTerminal

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
