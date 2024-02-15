package kobold.parser.dsl.support

import kobold.matchers.Symbol

data class Expression(override val children: List<Symbol>) : Symbol {
    private val child : Expression? = null

    constructor(child: Expression? = null) :
            this(if (child == null) emptyList() else listOf(child as Symbol))

    override fun equals(other: Any?) =
        other != null && other is Expression && ((bothChildrenAreNull(other)) || (childrenAreEqual(other)))

    private fun childrenAreEqual(other: Expression): Boolean = child?.equals(other.child) ?: false

    private fun bothChildrenAreNull(other: Expression): Boolean = child == null && other.child == null
}