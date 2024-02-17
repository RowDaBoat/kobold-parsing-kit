package kobold.matchers

import kobold.lexer.dsl.NothingToken

typealias Tokens = Sequence<Token>

fun Tokens.firstOrNothing() =
    firstOrNull() ?: NothingToken()
