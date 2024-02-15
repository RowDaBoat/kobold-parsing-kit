package kobold.matchers

class None : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) = Rejected(rest)
}
