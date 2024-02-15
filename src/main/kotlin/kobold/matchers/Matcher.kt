package kobold.matchers

interface Matcher {
    fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): MatcherResult
}

fun Matcher.match(tokens: List<Token>) = this.match(tokens, tokens.asSequence())

private fun Matcher.match(tokens: List<Token>, rest: Tokens): MatcherResult =
    match(tokens, rest) { matcher, current -> matcher.match(tokens, current) }
