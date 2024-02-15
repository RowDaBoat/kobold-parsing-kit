package kobold.matchers

class Not(private val expression: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when (evaluate(expression, rest)) {
            is Accepted -> Rejected(rest)
            else -> Accepted(emptySequence(), rest)
        }
}
