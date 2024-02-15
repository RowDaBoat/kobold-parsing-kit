package kobold.lexer.dsl

import kobold.matchers.Token

fun tokensFrom(string: String) = string.split(" ").map { Token(it) }.filter { it.text != "" }
