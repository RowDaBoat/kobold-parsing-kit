package io.vexel.kobold

open class Token(val text: String): Symbol {
    var line: Int = 0
        private set

    var column: Int = 0
        private set

    override val children: List<Symbol> =
        emptyList()

    constructor(character: Char) : this(character.toString())

    fun addMetadata(lineNumber: Int, columnNumber: Int) {
        this.line = lineNumber
        this.column = columnNumber
    }

    override fun show(depth: Int) =
        indent(depth) + toString()

    override fun equals(other: Any?) =
        other is Token && text == other.text

    override fun hashCode() =
        31 * text.hashCode() + children.hashCode()

    override fun toString() =
        "${this.javaClass.simpleName}(text=\"$text\", line=$line, column=$column)"
}
