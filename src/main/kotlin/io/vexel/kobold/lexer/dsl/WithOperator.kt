package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.rules.LexerRule
import io.vexel.kobold.matchers.Concatenation
import io.vexel.kobold.matchers.Empty
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.TerminalByContent

interface WithOperatorDSL {
    infix fun String.with(producer: ((String) -> Token))
    infix fun Matcher.with(producer: ((String) -> Token))
}

class WithOperator(private val rules: MutableList<LexerRule>) : WithOperatorDSL {
    override fun String.with(producer: (String) -> Token) {
        val matcher = toMatcher()

        rules.add(LexerRule(matcher, producer))
    }

    override fun Matcher.with(producer: (String) -> Token) {
        rules.add(LexerRule(this, producer))
    }
}

fun String.toMatcher() = this
    .map { TerminalByContent(Token(it.toString())) }
    .fold(Empty() as Matcher) { acc, cur -> Concatenation(acc, cur) }
