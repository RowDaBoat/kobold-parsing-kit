package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Or should` {
    @Test
    fun `match either`() {
        val lexer = lexer {
            "hey" or "ya" with { Token(it) }
            ignore(" ")
        }

        val string = "heyya hey ya hey yahey"
        val tokens = lexer.tokenize(string)
        val expected = listOf("hey", "ya", "hey", "ya", "hey", "ya", "hey").map { Token(it) }
        assertEquals(expected, tokens)
    }

    @Test
    fun `not match either on invalid sequence`() {
        val lexer = lexer {
            "hey" or "ya" with { Token(it) }
            ignore(" ")
        }

        val string = "0 hey ya"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }
}
