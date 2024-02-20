package io.vexel.kobold.lexer

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.rules.LexerRule
import io.vexel.kobold.lexer.rules.RuleMatched

class LexerState(
    val text: String,
    private val lines: List<String>,
    private val rules: List<LexerRule>,
    val token: Token,
    private val line: Int = 1,
    private val column: Int = 1,
    private val start: Int = 0
) {
    fun nextState(): LexerState? {
        return when(val result = matchWithRules()) {
            is RuleMatched -> advanceState(result)
            else -> null
        }
    }

    private fun matchWithRules() =
        rules.asSequence()
            .map { it.match(text) }
            .firstOrNull { it is RuleMatched }

    private fun advanceState(result: RuleMatched): LexerState {
        val token = result.token
        val tokenText = token.text
        val newLineCount = tokenText.count { it == '\n' }
        val newLine = line + newLineCount
        val newColumn = getNewColumn(newLineCount, tokenText)
        val newStart = getNewLine(token.text.length)
        val lineText = lines[line - 1]

        token.addMetadata(lineText, line, column, start, newStart)

        return LexerState(result.rest, lines, rules, result.token, newLine, newColumn, newStart)
    }

    private fun getNewColumn(newLineCount: Int, tokenText: String) =
        when (newLineCount) {
            0 -> column + tokenText.length
            else -> tokenText.length - tokenText.lastIndexOf('\n')
        }

    private fun getNewLine(length: Int) =
        start + length

    override fun toString() =
        "State { string = $text token = ${token.text} }"
}
