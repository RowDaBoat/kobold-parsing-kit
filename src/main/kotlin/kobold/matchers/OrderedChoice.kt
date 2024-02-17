package kobold.matchers

import kobold.Accepted
import kobold.Rejected

class OrderedChoice(private val one: Matcher, private val other: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when(val result = evaluate(one, rest)) {
            is Accepted -> result
            is Rejected -> evaluate(other, rest)
        }
}
