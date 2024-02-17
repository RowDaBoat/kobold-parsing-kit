package kobold.lexer.rules

import kobold.Accepted
import kobold.Rejected
import kobold.Result
import kobold.matchers.*

//TODO: passing the string around and having to .asTokens() and .joinToString("") every time seems odd.
class ProducerRule(val matcher: Matcher, val producer: (String) -> Token) : Rule {
    override fun match(string: String) =
        when(val result = matcher.match(string.asTokens())) {
            is Accepted -> match(result)
            is Rejected -> RuleNotMatched()
        }

    private fun match(result: Result): RuleMatched {
        val matchingString = stringFrom(result.matched)
        val token = producer(matchingString)
        val rest = stringFrom(result.rest)
        return RuleMatched(token, rest)
    }

    private fun stringFrom(tokens: Sequence<Token>) =
        tokens.joinToString("") { it.text }

    private fun String.asTokens(): List<Token> = map { Token(it) }
}

