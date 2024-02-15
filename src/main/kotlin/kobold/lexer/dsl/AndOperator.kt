package kobold.lexer.dsl

import kobold.matchers.Concatenation
import kobold.matchers.Matcher
import kobold.matchers.Not

interface AndOperatorDSL {
    infix fun String.and(that: String): Matcher
    infix fun String.and(that: Matcher): Matcher
    infix fun Matcher.and(that: String): Matcher
    infix fun Matcher.and(that: Matcher): Matcher
}

class AndOperator : AndOperatorDSL {
    override infix fun String.and(that: String) = this.toMatcher() and that.toMatcher()

    override infix fun String.and(that: Matcher) = this.toMatcher() and that

    override infix fun Matcher.and(that: String) = this and that.toMatcher()

    override infix fun Matcher.and(that: Matcher) = Concatenation(Not(Not(that)), this)
}
