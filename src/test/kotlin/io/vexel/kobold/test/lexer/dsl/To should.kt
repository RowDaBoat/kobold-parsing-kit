package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `To should` {
    @Test
    fun `match characters in range`() {
        val lexer = lexer { '0' to '9' with { Token(it) } }

        val string = "0123456789"
        val tokens = lexer.tokenize(string)
        val expected = string.map { Token(it) }.toList()
        assertEquals(expected, tokens)
    }

    @Test
    fun `not match characters out of range`() {
        val lexer = lexer { '0' to '9' with { Token(it) } }

        val string = "abc01010"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }
}
