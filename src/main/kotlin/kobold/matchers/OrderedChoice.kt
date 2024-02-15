package kobold.matchers

class OrderedChoice(private val one: Matcher, private val other: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when(val result = evaluate(one, rest)) {
            is Accepted -> result
            else -> evaluate(other, rest)
        }
}
