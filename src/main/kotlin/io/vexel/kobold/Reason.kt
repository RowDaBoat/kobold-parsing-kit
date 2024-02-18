package io.vexel.kobold

sealed class Reason {
    abstract fun show(): String
}

class UnexpectedToken(
    val detected: Token,
    val expectedTokens: Sequence<Token>,
    val expectedTokenTypes: Sequence<Class<*>>
) : Reason() {
    override fun show() =
        "Unexpected token $detected, expected " + showExpected() + "."

    private fun showExpected() =
        expectedNames().let {
            when (it.any()) {
                true -> it.dropLast(1).joinToString(", ") + " or " + it.last()
                false -> ""
            }
        }

    private fun expectedNames() = expectedTokens
        .map { "'${it.text}'" }
        .plus(expectedTokenTypes.map { "'${it.javaClass.simpleName}'" })
        .toList()
}

object NoRemainingTokens : Reason() {
    override fun show() =
        "Unexpected end of input."
}

class TrailingTokens(val trail: Sequence<Token>) : Reason() {
    override fun show() =
        "Input cannot be completely parsed, trailing input remains: ${showTrail(trail)}."

    private fun showTrail(trail: Sequence<Token>) =
        trail.take(10).joinToString(" ") { it.text }
}

object UnsupportedLeftRecursion : Reason() {
    override fun show() =
        "Input encountered an unsupported left-recursive case. This is a bug in the grammar."
}
