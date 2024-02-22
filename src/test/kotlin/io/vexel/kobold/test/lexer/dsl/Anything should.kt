package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Anything should` {
    @Test
    fun `match any character`() {
        val lexer = lexer {
            anything then anything then anything with { Token(it) }
        }

        val string = "hey"
        val tokens = lexer.tokenize(string)
        val expected = listOf(Token(string))
        assertEquals(expected, tokens)
    }
}