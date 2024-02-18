package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.matchers.Concatenation
import io.vexel.kobold.matchers.Empty
import io.vexel.kobold.matchers.Matcher

interface SequenceOperatorDSL {
    fun sequence(vararg that: String): Matcher
    fun sequence(vararg that: Matcher): Matcher
    fun sequence(that: List<Matcher>): Matcher
}

class SequenceOperator : SequenceOperatorDSL {
    override fun sequence(vararg that: String) = sequenceOf(that.map { it.toMatcher() }.toTypedArray())

    override fun sequence(vararg that: Matcher) = sequenceOf(that)

    override fun sequence(that: List<Matcher>): Matcher = sequenceOf(that.toTypedArray())

    private fun sequenceOf(that: Array<out Matcher>) = that.fold(Empty() as Matcher)
        { partial, matcher -> Concatenation(partial, matcher) }
}
