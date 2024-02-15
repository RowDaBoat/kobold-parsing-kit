package kobold.lexer.dsl

import kobold.lexer.rules.ProducerRule
import kobold.lexer.rules.Rule
import kobold.matchers.*

interface WithOperatorDSL {
    infix fun String.with(producer: ((String) -> Token))
    infix fun Matcher.with(producer: ((String) -> Token))
}

class WithOperator(private val rules: MutableList<Rule>) : WithOperatorDSL {
    override fun String.with(producer: (String) -> Token) {
        val matcher = toMatcher()

        rules.add(ProducerRule(matcher, producer))
    }

    override fun Matcher.with(producer: (String) -> Token) {
        rules.add(ProducerRule(this, producer))
    }
}

fun String.toMatcher() = this
    .map { TerminalByContent(Token(it.toString())) }
    .fold(Empty() as Matcher) { acc, cur -> Concatenation(acc, cur) }
