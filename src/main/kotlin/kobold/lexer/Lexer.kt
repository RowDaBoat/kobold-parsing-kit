package kobold.lexer

import MatcherMemo
import kobold.lexer.dsl.IgnoredToken
import kobold.lexer.dsl.LexerDSL
import kobold.lexer.dsl.NothingToken
import kobold.lexer.rules.Rule
import kobold.matchers.Token

fun lexer(ruleDeclarations: LexerDSL.() -> Unit): Lexer {
    val rules = mutableListOf<Rule>()
    val memo = MatcherMemo()
    val lexer = LexerDSL(rules, memo)
    lexer.ruleDeclarations()
    return Lexer(rules)
}

class Lexer(private val rules: MutableList<Rule>) {
    fun tokenize(string: String): List<Token> =
        generateSequence(LexerState(string, rules, NothingToken())) { it.nextState() }
            .filter { it.token !is NothingToken }
            .filter { it.token !is IgnoredToken }
            .map { it.token }
            .toList()
}
