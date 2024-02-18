package kobold.matchers

import kobold.*

class TerminalByType<T>(private val klass: Class<T>) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): Result {
        val position = tokens.count() - rest.count()

        return when {
            !rest.any() -> Rejected(NoRemainingTokens, rest)
            tokens[position].javaClass == klass -> accepted(rest, tokens, position)
            else -> unexpectedToken(tokens, position, rest)
        }
    }

    private fun unexpectedToken(tokens: List<Token>, position: Int, rest: Tokens) =
        Rejected(UnexpectedToken(tokens[position], emptySequence(), sequenceOf(klass)), rest)

    private fun accepted(rest: Tokens, tokens: List<Token>, position: Int) =
        Accepted(rest.take(1), rest.drop(1), tokens[position])
}
