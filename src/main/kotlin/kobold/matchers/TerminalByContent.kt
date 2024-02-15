package kobold.matchers

class TerminalByContent(private val toMatch: Token) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): MatcherResult {
        val position = tokens.count() - rest.count()

        return when (rest.any() && tokens[position] == toMatch) {
            true -> Accepted(rest.take(1), rest.drop(1), tokens[position])
            false -> Rejected(rest)
        }
    }
}

class TerminalByType<T>(private val klass :Class<T>) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): MatcherResult {
        val position = tokens.count() - rest.count()

        return when (rest.any() && tokens[position].javaClass == klass) {
            true -> Accepted(rest.take(1), rest.drop(1), tokens[position])
            false -> Rejected(rest)
        }
    }
}
