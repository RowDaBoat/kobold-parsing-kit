package kobold.lexer

import kobold.lexer.rules.Rule
import kobold.lexer.rules.RuleMatched
import kobold.matchers.Token

class LexerState(val string: String, private val rules: List<Rule>, val token: Token? = null) {
    fun nextState(): LexerState? {
        val result = rules.asSequence()
            .map { it.match(string) }
            .firstOrNull { it is RuleMatched }

        return when(result) {
            is RuleMatched -> { LexerState(result.rest, rules, result.token) }
            else -> null
        }
    }

    override fun toString() =
        "State { string = $string token = ${token?.text} }"
}
