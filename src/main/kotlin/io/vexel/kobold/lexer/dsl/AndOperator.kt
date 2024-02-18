package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.matchers.Concatenation
import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.Not

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
