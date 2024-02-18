package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.rules.LexerRule
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.TerminalByContent

interface IgnoreOperatorDSL {
    fun ignore(token: String)
    fun ignore(matcher: Matcher)
}

class IgnoreOperator(private val rules: MutableList<LexerRule>) : IgnoreOperatorDSL  {
    override fun ignore(token: String) = ignore(TerminalByContent(Token(token)))

    override fun ignore(matcher: Matcher) {
        rules.add(LexerRule(matcher) { IgnoredToken(it) })
    }
}
