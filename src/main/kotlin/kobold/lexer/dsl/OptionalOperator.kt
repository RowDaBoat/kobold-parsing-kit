package kobold.lexer.dsl

import kobold.matchers.Empty
import kobold.matchers.Matcher
import kobold.matchers.OrderedChoice

interface OptionalOperatorDSL {
    fun optional(that: String): Matcher
    fun optional(that: Matcher): Matcher
}

class OptionalOperator : OptionalOperatorDSL {
    override fun optional(that: String) = optional(that.toMatcher())

    override fun optional(that: Matcher) = OrderedChoice(that, Empty())
}
