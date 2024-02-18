package io.vexel.kobold.matchers

import io.vexel.kobold.Token

class None : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        io.vexel.kobold.Rejected(reason(rest), rest)

    private fun reason(rest: Tokens) =
        when {
            rest.any() -> io.vexel.kobold.UnexpectedToken(rest.first(), emptySequence(), emptySequence())
            else -> io.vexel.kobold.NoRemainingTokens
        }
}
