package kobold.matchers

sealed class MatcherResult(val matched: Sequence<Token>, val rest: Sequence<Token>)

class Accepted(
    matched: Sequence<Token>,
    rest: Sequence<Token>,
    val tree: Symbol? = null,
    private val children: List<Symbol> = emptyList()
) : MatcherResult(matched, rest) {
    fun concatenate(other: Accepted) =
        Accepted(matched.plus(other.matched), other.rest, null, subtreeOrChildren().plus(other.subtreeOrChildren()))

    fun withProducer(producer: ((List<Symbol>) -> Symbol)?) =
        when (producer) {
            null -> this
            else -> Accepted(matched, rest, producer(subtreeOrChildren()), emptyList())
        }

    private fun subtreeOrChildren() = when (tree) {
        null -> children
        else -> listOf(tree)
    }
}

class Rejected(rest: Sequence<Token>) :
    MatcherResult(emptySequence(), rest)

class DetectingRecursion(rest: Sequence<Token>) :
    MatcherResult(emptySequence(), rest)
