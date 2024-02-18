package io.vexel.kobold.matchers

import io.vexel.kobold.Token

interface Matcher {
    fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): io.vexel.kobold.Result
}

fun Matcher.match(tokens: List<Token>) = this.match(tokens, tokens.asSequence())

private fun Matcher.match(tokens: List<Token>, rest: Tokens): io.vexel.kobold.Result =
    match(tokens, rest) { matcher, current -> matcher.match(tokens, current) }
