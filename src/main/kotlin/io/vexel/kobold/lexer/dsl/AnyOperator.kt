package io.vexel.kobold.lexer.dsl

import io.vexel.kobold.matchers.Anything
import io.vexel.kobold.matchers.Matcher

interface AnyOperatorDSL {
    fun any(): Matcher
}

class AnyOperator : AnyOperatorDSL {
    override fun any() = Anything()
}
