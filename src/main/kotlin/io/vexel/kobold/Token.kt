package io.vexel.kobold

open class Token(val text: String): Symbol {
    var lineNumber: Int = 0
        private set

    var columnNumber: Int = 0
        private set

    var line: String = ""
        private set

    override val children: List<Symbol> = emptyList()

    constructor(character: Char) : this(character.toString())

    fun addMetadata(lineNumber: Int, columnNumber: Int, s: String) {
        this.lineNumber = lineNumber
        this.columnNumber = columnNumber
        this.line = line
    }

    override fun equals(other: Any?) = other is Token && text == other.text

    override fun hashCode() = 31 * text.hashCode() + children.hashCode()

    override fun toString() =
        "${this.javaClass.simpleName}(text=\"$text\", line=$lineNumber, column=$columnNumber)"
}
