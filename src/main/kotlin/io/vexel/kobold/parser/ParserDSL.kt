package io.vexel.kobold.parser

import MatcherMemo
import io.vexel.kobold.matchers.Empty
import io.vexel.kobold.parser.dsl.*

class ParserDSL(private val memo : MatcherMemo = MatcherMemo()) :
    OrOperatorDSL by OrOperator(),
    ThenOperatorDSL by ThenOperator(),
    NotOperatorDSL by NotOperator(),
    NonTerminalOperatorDSL by NonTerminalOperators(memo),
    TerminalOperatorDSL by TerminalOperators(),
    SequenceOperatorDSL by SequenceOperator(),
    AnyOfOperatorDSL by AnyOfOperator(),
    ZeroOrMoreOperatorDSL by ZeroOrMoreOperator(memo),
    OneOrMoreOperatorDSL by OneOrMoreOperator(memo),
    AndOperatorDSL by AndOperator(),
    AnyOperatorDSL by AnyOperator(),
    OptionalOperatorDSL by OptionalOperator() {
    val empty = Empty()
}

inline fun<reified T> ParserDSL.terminal() =
    terminal(T::class.java)
