package kobold.matchers

import kobold.Accepted
import kobold.Rejected

class Not(private val expression: Matcher) : Matcher {
    override fun match(tokens: List<Token>, rest: Tokens, evaluate: Evaluator) =
        when (evaluate(expression, rest)) {
            is Accepted -> Rejected(rest.firstOrNothing(), rest)
            is Rejected -> Accepted(emptySequence(), rest)
        }
}
