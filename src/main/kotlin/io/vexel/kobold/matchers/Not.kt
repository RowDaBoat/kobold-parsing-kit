package io.vexel.kobold.matchers

import io.vexel.kobold.Token

class Not(private val expression: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when (evaluate(expression, rest)) {
            is io.vexel.kobold.Accepted -> io.vexel.kobold.Rejected(reason(rest), rest)
            is io.vexel.kobold.Rejected -> io.vexel.kobold.Accepted(emptySequence(), rest)
        }

    private fun reason(rest: Tokens) =
        when {
            rest.any() -> io.vexel.kobold.UnexpectedToken(rest.first(), emptySequence(), emptySequence())
            else -> io.vexel.kobold.NoRemainingTokens
        }
}
