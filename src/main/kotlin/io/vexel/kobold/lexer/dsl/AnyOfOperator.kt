package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.matchers.Matcher
import io.vexel.kobold.matchers.None
import io.vexel.kobold.matchers.OrderedChoice

interface AnyOfOperatorDSL {
    fun anyOf(vararg that: String): Matcher
    fun anyOf(vararg that: Matcher): Matcher
    fun anyOf(that: List<Matcher>): Matcher
}

class AnyOfOperator : AnyOfOperatorDSL {
    override fun anyOf(vararg that: String) = anyElementOf(that.map { it.toMatcher() }.toTypedArray())

    override fun anyOf(vararg that: Matcher) = anyElementOf(that)

    override fun anyOf(that: List<Matcher>) = anyElementOf(that.toTypedArray())

    private fun anyElementOf(that: Array<out Matcher>) = that.fold(None() as Matcher) {
        partial, matcher ->
        OrderedChoice(partial, matcher)
    }
}
