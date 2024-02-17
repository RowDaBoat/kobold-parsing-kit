package kobold.parser

import kobold.Accepted
import kobold.Rejected
import kobold.matchers.Matcher
import kobold.matchers.Token
import kobold.matchers.match
import kobold.parser.dsl.ParserDSL

fun parser(rules: ParserDSL.() -> Matcher) = Parser(ParserDSL().rules())

class Parser(private val matcher: Matcher) {
    fun parse(tokens: List<Token>) =
        when(val result = matcher.match(tokens)) {
            is Accepted -> acceptIfNoTrailingTokens(result)
            is Rejected -> result
        }

    private fun acceptIfNoTrailingTokens(result: Accepted) =
        when (result.rest.any()) {
            true -> Rejected(result.rest.first(), result.rest)
            false -> result
        }
}
