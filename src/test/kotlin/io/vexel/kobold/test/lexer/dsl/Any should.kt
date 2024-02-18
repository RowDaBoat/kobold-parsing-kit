package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Any should` {
    @Test
    fun `match any character`() {
        val lexer = lexer {
            any() then any() then any() with { Token(it) }
        }

        val string = "hey"
        val tokens = lexer.tokenize(string)
        val expected = listOf(Token(string))
        assertEquals(expected, tokens)
    }
}