package io.vexel.kobold.lexer.rules

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.Result
import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.match

class LexerRule(val matcher: Matcher, val producer: (String) -> Token) {
    fun match(string: String) =
        when(val result = matcher.match(string.asTokens())) {
            is Accepted -> matched(result)
            is Rejected -> RuleNotMatched()
        }

    private fun matched(result: Result): RuleMatched {
        val matchingString = stringFrom(result.matched)
        val token = producer(matchingString)
        //TODO: doing .joinToString("") here every time seems suspicious.
        val rest = stringFrom(result.rest)
        return RuleMatched(token, rest)
    }

    private fun stringFrom(tokens: Sequence<Token>) =
        tokens.joinToString("") { it.text }

    private fun String.asTokens(): List<Token> = map { Token(it) }
}
