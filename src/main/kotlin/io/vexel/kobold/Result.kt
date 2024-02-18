package io.vexel.kobold

sealed class Result(val matched: Sequence<Token>, val rest: Sequence<Token>)

class Accepted(
    matched: Sequence<Token>,
    rest: Sequence<Token>,
    val tree: Symbol? = null,
    private val children: List<Symbol> = emptyList()
) : Result(matched, rest) {
    fun concatenate(other: Accepted): Accepted {
        val newMatched = matched.plus(other.matched)
        val newChildren = subtreeOrChildren().plus(other.subtreeOrChildren())
        return Accepted(newMatched, other.rest, null, newChildren)
    }

    fun withProducer(producer: ((List<Symbol>) -> Symbol)?) =
        when (producer) {
            null -> this
            else -> Accepted(matched, rest, producer(subtreeOrChildren()), emptyList())
        }

    private fun subtreeOrChildren() =
        when (tree) {
            null -> children
            else -> listOf(tree)
        }
}

class Rejected(
    val reason: Reason,
    rest: Sequence<Token>
) : Result(emptySequence(), rest) {
    val column: Int
    val line: Int

    init {
        val firstUnmatchedToken = rest.firstOrNull()
        column = firstUnmatchedToken?.column ?: 1
        line = firstUnmatchedToken?.line ?: 1
    }
}
