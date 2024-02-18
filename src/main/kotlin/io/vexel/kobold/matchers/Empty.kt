package io.vexel.kobold.matchers

import io.vexel.kobold.Token

class Empty : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        io.vexel.kobold.Accepted(emptySequence(), rest)
}
