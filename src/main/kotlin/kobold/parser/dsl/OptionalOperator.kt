package kobold.parser.dsl

import kobold.matchers.Token
import kobold.matchers.Empty
import kobold.matchers.Matcher
import kobold.matchers.OrderedChoice
import kobold.matchers.TerminalByContent

interface OptionalOperatorDSL {
    fun optional(that: String): Matcher
    fun optional(that: Token): Matcher
    fun optional(that: Matcher): Matcher
}

class OptionalOperator : OptionalOperatorDSL {
    override fun optional(that: String) = optional(Token(that))
    override fun optional(that: Token) = optional(TerminalByContent(that))
    override fun optional(that: Matcher) = OrderedChoice(that, Empty())
}