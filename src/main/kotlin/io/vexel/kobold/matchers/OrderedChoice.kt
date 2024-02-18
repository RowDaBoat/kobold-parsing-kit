package io.vexel.kobold.matchers

import io.vexel.kobold.Token

class OrderedChoice(private val one: Matcher, private val other: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when(val result = evaluate(one, rest)) {
            is io.vexel.kobold.Accepted -> result
            is io.vexel.kobold.Rejected -> getOther(evaluate, rest, result)
        }

    private fun getOther(evaluate: Evaluator, rest: Tokens, firstResult: io.vexel.kobold.Rejected) =
        when(val result = evaluate(other, rest)) {
            is io.vexel.kobold.Accepted -> result
            is io.vexel.kobold.Rejected -> io.vexel.kobold.Rejected(reasonFrom(firstResult.reason, result.reason), rest)
        }

    private fun reasonFrom(first: io.vexel.kobold.Reason, second: io.vexel.kobold.Reason): io.vexel.kobold.Reason {
        return when {
            first is io.vexel.kobold.NoRemainingTokens && second is io.vexel.kobold.NoRemainingTokens -> io.vexel.kobold.NoRemainingTokens
            first is io.vexel.kobold.UnexpectedToken && second is io.vexel.kobold.UnexpectedToken -> composeUnexpected(first, second)
            first is io.vexel.kobold.UnexpectedToken -> first
            else -> second
        }
    }

    private fun composeUnexpected(first: io.vexel.kobold.UnexpectedToken, second: io.vexel.kobold.UnexpectedToken) =
        io.vexel.kobold.UnexpectedToken(
            first.detected,
            first.expectedTokens.plus(second.expectedTokens),
            first.expectedTokenTypes.plus(second.expectedTokenTypes)
        )
}
