package kobold.parser

import kobold.parser.dsl.ParserDSL
import kobold.matchers.Matcher
import kobold.matchers.MatcherResult
import kobold.matchers.Token
import kobold.matchers.match

fun parser(rules: ParserDSL.() -> Matcher) = Parser(ParserDSL().rules())

class Parser(private val matcher: Matcher) {
    fun parse(tokens: List<Token>): MatcherResult =
        matcher.match(tokens)
}
