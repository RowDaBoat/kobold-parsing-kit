package kobold.lexer

import MatcherMemo
import kobold.lexer.dsl.LexerDSL
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
        generateSequence(LexerState(string, rules)) { it.nextState() }
            .mapNotNull { it.token }
            .toList()
}
