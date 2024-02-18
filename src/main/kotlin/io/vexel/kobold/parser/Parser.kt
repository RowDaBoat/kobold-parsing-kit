package io.vexel.kobold.parser

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.Token
import io.vexel.kobold.TrailingTokens
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.match

fun parser(rules: ParserDSL.() -> Matcher) = Parser(ParserDSL().rules())

class Parser(private val matcher: Matcher) {
    fun parse(tokens: List<Token>) =
        when(val result = matcher.match(tokens)) {
            is Accepted -> acceptIfNoTrailingTokens(result)
            is Rejected -> result
        }

    private fun acceptIfNoTrailingTokens(result: Accepted) =
        when (result.rest.any()) {
            true -> Rejected(TrailingTokens(result.rest), result.rest)
            false -> result
        }
}
