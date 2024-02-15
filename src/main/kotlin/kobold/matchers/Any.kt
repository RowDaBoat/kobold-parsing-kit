package kobold.matchers

class Any : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator): MatcherResult {
        return when (rest.any()) {
            true -> Accepted(rest.take(1), rest.drop(1), tokens[tokens.count() - rest.count()])
            false -> Rejected(rest)
        }
    }
}
