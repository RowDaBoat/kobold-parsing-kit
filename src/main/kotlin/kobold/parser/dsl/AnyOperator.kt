package kobold.parser.dsl

import kobold.matchers.Any
import kobold.matchers.Matcher

interface AnyOperatorDSL {
    fun any(): Matcher
}

class AnyOperator : AnyOperatorDSL {
    override fun any() = Any()
}
