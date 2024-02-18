package kobold.matchers

import kobold.*

class TerminalByContent(private val toMatch: Token) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): Result {
        val position = tokens.count() - rest.count()

        return when {
            !rest.any() -> Rejected(NoRemainingTokens, rest)
            tokens[position] == toMatch -> accepted(rest, tokens, position)
            else -> unexpectedToken(tokens, position, rest)
        }
    }

    private fun accepted(rest: Tokens, tokens: List<Token>, position: Int) =
        Accepted(rest.take(1), rest.drop(1), tokens[position])

    private fun unexpectedToken(tokens: List<Token>, position: Int, rest: Tokens) =
        Rejected(UnexpectedToken(tokens[position], sequenceOf(toMatch), emptySequence()), rest)
}
