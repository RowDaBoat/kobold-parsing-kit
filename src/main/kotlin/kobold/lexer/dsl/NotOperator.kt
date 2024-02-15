package kobold.lexer.dsl

import kobold.matchers.Matcher
import kobold.matchers.Not

interface NotOperatorDSL {
    fun not(that: String): Matcher
    fun not(that: Matcher): Matcher
}

class NotOperator : NotOperatorDSL {
    override fun not(that: String) = not(that.toMatcher())

    override fun not(that: Matcher) = Not(that)
}
