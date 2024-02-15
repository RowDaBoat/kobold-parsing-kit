package kobold.parser.dsl.support

import kobold.matchers.Symbol
import kobold.matchers.Token

data class Enclosure(override val children: List<Symbol>): Symbol {
    constructor(vararg children: Symbol) : this(listOf(Token("(")).plus(children.toList().plus(Token(")"))))

    override fun toString() = "(${children.drop(1).dropLast(1).joinToString("")})"
}