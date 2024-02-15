package kobold.lexer.rules

import kobold.matchers.Accepted
import kobold.matchers.Matcher
import kobold.matchers.Token
import kobold.matchers.match

class IgnoreRule(val matcher: Matcher) : Rule {
    override fun match(string: String) =
        when(val result = matcher.match(string.asTokens())) {
            is Accepted -> RuleMatched(null, result.rest.joinToString("") { it.text })
            else -> RuleNotMatched()
        }

    private fun String.asTokens(): List<Token> = map { Token(it) }
}
