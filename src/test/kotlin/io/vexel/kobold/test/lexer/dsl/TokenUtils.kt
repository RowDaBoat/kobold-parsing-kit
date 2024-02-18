package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token

fun tokensFrom(string: String) = string.split(" ").map { Token(it) }.filter { it.text != "" }
