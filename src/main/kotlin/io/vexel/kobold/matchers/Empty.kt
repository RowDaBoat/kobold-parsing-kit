package io.vexel.kobold.matchers

import io.vexel.kobold.Accepted
import io.vexel.kobold.Token

class Empty : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        Accepted(emptySequence(), rest)
}
