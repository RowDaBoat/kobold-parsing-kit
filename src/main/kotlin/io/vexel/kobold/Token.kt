package io.vexel.kobold

open class Token(val text: String): Symbol {
    var lineText: String = ""
        private set

    var line: Int = 0
        private set

    var column: Int = 0
        private set

    var start: Int = 0
        private set

    var end: Int = 0
        private set

    override val children: List<Symbol> =
        emptyList()

    constructor(character: Char) : this(character.toString())

    fun addMetadata(lineText: String, line: Int, column: Int, start: Int, end: Int) {
        this.lineText = lineText
        this.line = line
        this.column = column
        this.start = start
        this.end = end
    }

    override fun show(depth: Int) =
        indent(depth) + toString()

    override fun equals(other: Any?) =
        other is Token && text == other.text

    override fun hashCode() =
        31 * text.hashCode() + children.hashCode()

    override fun toString() =
        "${this.javaClass.simpleName}(text=\"$text\", line=$line, column=$column, start=$start, end=$end)"
}
