package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.matchers.Empty
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.OrderedChoice

interface OptionalOperatorDSL {
    fun optional(that: String): Matcher
    fun optional(that: Matcher): Matcher
}

class OptionalOperator : OptionalOperatorDSL {
    override fun optional(that: String) = optional(that.toMatcher())

    override fun optional(that: Matcher) = OrderedChoice(that, Empty())
}
