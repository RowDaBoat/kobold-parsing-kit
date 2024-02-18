package io.vexel.kobold.lexer

import MatcherMemo
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
    fun tokenize(string: String): List<Token> =
        generateSequence(LexerState(string, rules, NothingToken())) { it.nextState() }
            .filter { it.token !is NothingToken }
            .filter { it.token !is IgnoredToken }
            .map { it.token }
            .toList()
}
