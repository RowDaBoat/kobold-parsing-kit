package io.vexel.kobold.matchers

import io.vexel.kobold.Token

class TerminalByContent(private val toMatch: Token) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): io.vexel.kobold.Result {
        val position = tokens.count() - rest.count()

        return when {
            !rest.any() -> io.vexel.kobold.Rejected(io.vexel.kobold.NoRemainingTokens, rest)
            tokens[position] == toMatch -> accepted(rest, tokens, position)
            else -> unexpectedToken(tokens, position, rest)
        }
    }

    private fun accepted(rest: Tokens, tokens: List<Token>, position: Int) =
        io.vexel.kobold.Accepted(rest.take(1), rest.drop(1), tokens[position])

    private fun unexpectedToken(tokens: List<Token>, position: Int, rest: Tokens) =
        io.vexel.kobold.Rejected(
            io.vexel.kobold.UnexpectedToken(
                tokens[position],
                sequenceOf(toMatch),
                emptySequence()
            ), rest
        )
}
