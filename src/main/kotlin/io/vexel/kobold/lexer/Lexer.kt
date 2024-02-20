package io.vexel.kobold.lexer

import io.vexel.kobold.matchers.MatcherMemo
import io.vexel.kobold.Token
import io.vexel.kobold.lexer.dsl.IgnoredToken
import io.vexel.kobold.lexer.dsl.NothingToken
import io.vexel.kobold.lexer.rules.LexerRule

fun lexer(ruleDeclarations: LexerDSL.() -> Unit): Lexer {
    val rules = mutableListOf<LexerRule>()
    val memo = MatcherMemo()
    val lexer = LexerDSL(rules, memo)
    lexer.ruleDeclarations()
    return Lexer(rules)
}

class Lexer(private val rules: MutableList<LexerRule>) {
    fun tokenize(text: String): List<Token> {
        val lines = text.split('\n')
        return generateSequence(LexerState(text, lines, rules, NothingToken())) { it.nextState() }
            .filter { it.token !is NothingToken }
            .filter { it.token !is IgnoredToken }
            .map { it.token }
            .toList()
    }
}
