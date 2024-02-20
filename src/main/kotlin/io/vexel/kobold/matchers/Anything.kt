package io.vexel.kobold.matchers

import io.vexel.kobold.Accepted
import io.vexel.kobold.NoRemainingTokens
import io.vexel.kobold.Rejected
import io.vexel.kobold.Token

class Anything : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when (rest.any()) {
            true -> Accepted(rest.take(1), rest.drop(1), tokens[tokens.count() - rest.count()])
            false -> Rejected(NoRemainingTokens, rest)
        }
}
