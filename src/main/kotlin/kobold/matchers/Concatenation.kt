package kobold.matchers

class Concatenation(private val one: Matcher, private val other: Matcher) : Matcher {
    override fun match(
        tokens: List<Token>,
        rest: Sequence<Token>,
        evaluate: (Matcher, Sequence<Token>) -> MatcherResult
    ) = when(val oneResult = evaluate(one, rest)) {
        is Accepted -> getOther(oneResult, rest, evaluate)
        else -> Rejected(rest)
    }

    private fun getOther(
        oneResult: Accepted,
        rest: Sequence<Token>,
        evaluate: (Matcher, Sequence<Token>) -> MatcherResult
    ) = when (val otherResult = evaluate(other, oneResult.rest)) {
        is Accepted -> oneResult.concatenate(otherResult)
        else -> Rejected(rest)
    }
}
