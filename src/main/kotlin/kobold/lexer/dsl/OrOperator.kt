package kobold.lexer.dsl

import kobold.matchers.Matcher
import kobold.matchers.OrderedChoice

interface OrOperatorDSL {
    infix fun String.or(that: String): Matcher
    infix fun String.or(that: Matcher): Matcher

    infix fun Matcher.or(that: String): Matcher
    infix fun Matcher.or(that: Matcher): Matcher
}

class OrOperator : OrOperatorDSL {
    override infix fun String.or(that: String) = this.toMatcher() or that.toMatcher()
    override infix fun String.or(that: Matcher) = this.toMatcher() or that

    override infix fun Matcher.or(that: String) = this or that.toMatcher()
    override infix fun Matcher.or(that: Matcher) = OrderedChoice(this, that)
}
