package kobold.lexer.rules

import kobold.matchers.Accepted
import kobold.matchers.Matcher
import kobold.matchers.Token
import kobold.matchers.match

class ProducerRule(val matcher: Matcher, val producer: (String) -> Token) : Rule {
    override fun match(string: String) =
        when(val result = matcher.match(string.asTokens())) {
            is Accepted -> RuleMatched(producer(stringFrom(result.matched)), stringFrom(result.rest))
            else -> RuleNotMatched()
        }

    private fun stringFrom(sequence: Sequence<Token>) = sequence.joinToString("") { it.text }

    private fun String.asTokens(): List<Token> = map { Token(it) }
}

