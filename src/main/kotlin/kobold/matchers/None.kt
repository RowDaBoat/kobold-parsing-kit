package kobold.matchers

import kobold.NoRemainingTokens
import kobold.Rejected
import kobold.UnexpectedToken

class None : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        Rejected(reason(rest), rest)

    private fun reason(rest: Tokens) =
        when {
            rest.any() -> UnexpectedToken(rest.first(), emptySequence(), emptySequence())
            else -> NoRemainingTokens
        }
}
