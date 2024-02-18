package io.vexel.kobold.parser.dsl

import io.vexel.kobold.matchers.Any
import io.vexel.kobold.matchers.Matcher

interface AnyOperatorDSL {
    fun any(): Matcher
}

class AnyOperator : AnyOperatorDSL {
    override fun any() = Any()
}
