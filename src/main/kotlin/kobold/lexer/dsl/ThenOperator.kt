package kobold.lexer.dsl

import kobold.matchers.Concatenation
import kobold.matchers.Matcher

interface ThenOperatorDSL {
    infix fun String.then(that: String): Matcher
    infix fun String.then(that: Matcher): Matcher

    infix fun Matcher.then(that: String): Matcher
    infix fun Matcher.then(that: Matcher): Matcher
}

class ThenOperator : ThenOperatorDSL {
    override infix fun String.then(that: String) = this.toMatcher() then that.toMatcher()
    override infix fun String.then(that: Matcher) = this.toMatcher() then that

    override infix fun Matcher.then(that: String) = this then that.toMatcher()
    override infix fun Matcher.then(that: Matcher) = Concatenation(this, that)
}
