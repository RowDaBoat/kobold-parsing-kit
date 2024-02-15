package kobold.tokens

import kobold.matchers.Symbol

class Sum(override val children: List<Symbol>) : Symbol {
    constructor(vararg children: Symbol) : this(children.toList())
    override fun equals(other: Any?): Boolean = other is Sum && other.children == children
    override fun hashCode() = children.hashCode()
}