package kobold.lexer.dsl

import MatcherMemo
import kobold.lexer.rules.Rule

class LexerDSL(
    private val rules : MutableList<Rule> = mutableListOf(),
    private val memo: MatcherMemo = MatcherMemo()
) :
    WithOperatorDSL by WithOperator(rules),
    IgnoreOperatorDSL by IgnoreOperator(rules),
    ToOperatorDSL by ToOperator(),
    OrOperatorDSL by OrOperator(),
    ThenOperatorDSL by ThenOperator(),
    AnyOfOperatorDSL by AnyOfOperator(),
    AnyOperatorDSL by AnyOperator(),
    SequenceOperatorDSL by SequenceOperator(),
    ZeroOrMoreOperatorDSL by ZeroOrMoreOperator(memo),
    OneOrMoreOperatorDSL by OneOrMoreOperator(memo),
    AndOperatorDSL by AndOperator(),
    NotOperatorDSL by NotOperator(),
    OptionalOperatorDSL by OptionalOperator()
