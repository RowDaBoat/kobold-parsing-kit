package kobold.matchers

import kotlin.Any

open class Token(val text: String): Symbol {
    override val children: List<Symbol> = emptyList()
    constructor(character: Char) : this(character.toString())

    override fun equals(other: Any?) = other is Token && text == other.text

    override fun hashCode() = 31 * text.hashCode() + children.hashCode()

    override fun toString() = "${this.javaClass.simpleName}(text=\"$text\")"
}
