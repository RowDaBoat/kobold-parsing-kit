package io.vexel.kobold

interface Symbol {
    val children: List<Symbol>

    fun show(depth: Int = 0): String =
        showThis(depth) + showChildren(depth)

    private fun showChildren(depth: Int) =
        children.joinToString("\n") { it.show(depth + 1) }

    private fun showThis(depth: Int) =
        indent(depth) + javaClass.simpleName + "\n"
}

fun indent(depth: Int) =
    "  ".repeat(depth)
