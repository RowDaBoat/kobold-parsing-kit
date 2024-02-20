package io.vexel.kobold.parser

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Anything
import io.vexel.kobold.matchers.MatcherMemo
import io.vexel.kobold.matchers.Empty
import io.vexel.kobold.parser.dsl.*

class ParserDSL(private val memo : MatcherMemo = MatcherMemo()) :
    OrOperatorDSL by OrOperator(),
    ThenOperatorDSL by ThenOperator(),
    NotOperatorDSL by NotOperator(),
    NonTerminalOperatorDSL by NonTerminalOperators(memo),
    TerminalOperatorsDSL by TerminalOperators(),
    SequenceOperatorDSL by SequenceOperator(),
    AnyOfOperatorDSL by AnyOfOperator(),
    ZeroOrMoreOperatorDSL by ZeroOrMoreOperator(memo),
    OneOrMoreOperatorDSL by OneOrMoreOperator(memo),
    AndOperatorDSL by AndOperator(),
    OptionalOperatorDSL by OptionalOperator() {
    val anything = Anything()
    val empty = Empty()
}

inline fun<reified T: Token> ParserDSL.terminal() =
    terminal(T::class.java)
