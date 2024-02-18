package io.vexel.kobold

sealed class Reason

class UnexpectedToken(
    val detected: Token,
    val expectedTokens: Sequence<Token>,
    val expectedTokenTypes: Sequence<Class<*>>
) : Reason()

object NoRemainingTokens : Reason()

class TrailingTokens(val trail: Sequence<Token>) : Reason()

object UnsupportedLeftRecursion : Reason()
