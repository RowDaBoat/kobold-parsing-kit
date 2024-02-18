package kobold.matchers

import kobold.*

class OrderedChoice(private val one: Matcher, private val other: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when(val result = evaluate(one, rest)) {
            is Accepted -> result
            is Rejected -> getOther(evaluate, rest, result)
        }

    private fun getOther(evaluate: Evaluator, rest: Tokens, firstResult: Rejected) =
        when(val result = evaluate(other, rest)) {
            is Accepted -> result
            is Rejected -> Rejected(reasonFrom(firstResult.reason, result.reason), rest)
        }

    private fun reasonFrom(first: Reason, second: Reason): Reason {
        return when {
            first is NoRemainingTokens && second is NoRemainingTokens -> NoRemainingTokens
            first is UnexpectedToken && second is UnexpectedToken -> composeUnexpected(first, second)
            first is UnexpectedToken -> first
            else -> second
        }
    }

    private fun composeUnexpected(first: UnexpectedToken, second: UnexpectedToken) =
        UnexpectedToken(
            first.detected,
            first.expectedTokens.plus(second.expectedTokens),
            first.expectedTokenTypes.plus(second.expectedTokenTypes)
         )
}
