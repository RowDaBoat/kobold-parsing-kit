package kobold.lexer.dsl

import kobold.lexer.rules.ProducerRule
import kobold.lexer.rules.Rule
import kobold.matchers.Matcher
import kobold.matchers.TerminalByContent
import kobold.matchers.Token

interface IgnoreOperatorDSL {
    fun ignore(token: String)
    fun ignore(matcher: Matcher)
}

class IgnoreOperator(private val rules: MutableList<Rule>) : IgnoreOperatorDSL  {
    override fun ignore(token: String) = ignore(TerminalByContent(Token(token)))

    override fun ignore(matcher: Matcher) {
        rules.add(ProducerRule(matcher) { IgnoredToken(it) })
    }
}
