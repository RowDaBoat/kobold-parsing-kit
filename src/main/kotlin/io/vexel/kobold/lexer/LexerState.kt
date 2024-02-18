package io.vexel.kobold.lexer

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.rules.LexerRule
import io.vexel.kobold.lexer.rules.RuleMatched

class LexerState(
    val string: String,
    private val rules: List<LexerRule>,
    val token: Token,
    private val lineNumber: Int = 1,
    private val columnNumber: Int = 1
) {
    fun nextState(): LexerState? {
        return when(val result = matchWithRules()) {
            is RuleMatched -> advanceState(result)
            else -> null
        }
    }

    private fun matchWithRules() =
        rules.asSequence()
            .map { it.match(string) }
            .firstOrNull { it is RuleMatched }

    private fun advanceState(result: RuleMatched): LexerState {
        val token = result.token
        val tokenText = token.text
        val newLineCount = tokenText.count { it == '\n' }
        val newLineNumber = lineNumber + newLineCount
        val newColumnNumber = getNewColumnNumber(newLineCount, tokenText)

        token.addMetadata(lineNumber, columnNumber, "")

        return LexerState(result.rest, rules, result.token, newLineNumber, newColumnNumber)
    }

    private fun getNewColumnNumber(newLineCount: Int, tokenText: String) =
        when (newLineCount) {
            0 -> columnNumber + tokenText.length
            else -> tokenText.length - tokenText.lastIndexOf('\n')
        }

    override fun toString() =
        "State { string = $string token = ${token.text} }"
}
