package kobold.matchers

import kobold.*

class Not(private val expression: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when (evaluate(expression, rest)) {
            is Accepted -> Rejected(reason(rest), rest)
            is Rejected -> Accepted(emptySequence(), rest)
        }

    private fun reason(rest: Tokens) =
        when {
            rest.any() -> UnexpectedToken(rest.first(), emptySequence(), emptySequence())
            else -> NoRemainingTokens
        }
}
