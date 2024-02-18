package kobold.matchers

import kobold.Accepted
import kobold.Rejected
import kobold.lexer.dsl.NothingToken

class Concatenation(private val one: Matcher, private val other: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when(val oneResult = evaluate(one, rest)) {
            is Accepted -> getOther(oneResult, rest, evaluate)
            is Rejected -> Rejected(oneResult.reason, rest)
        }

    private fun getOther(oneResult: Accepted, rest: Tokens, evaluate: Evaluator) =
        when (val otherResult = evaluate(other, oneResult.rest)) {
            is Accepted -> oneResult.concatenate(otherResult)
            is Rejected -> Rejected(otherResult.reason, rest)
        }
}
