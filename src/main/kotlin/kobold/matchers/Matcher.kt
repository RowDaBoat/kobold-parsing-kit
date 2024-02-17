package kobold.matchers

import kobold.Result

interface Matcher {
    fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): Result
}

fun Matcher.match(tokens: List<Token>) = this.match(tokens, tokens.asSequence())

private fun Matcher.match(tokens: List<Token>, rest: Tokens): Result =
    match(tokens, rest) { matcher, current -> matcher.match(tokens, current) }
