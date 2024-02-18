package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `AnyOf should` {
    @Test
    fun `match either`() {
        val lexer = lexer {
            anyOf("hey", "ya", "hi") with { Token(it) }
            ignore(" ")
        }

        val string = "hey ya heyhihiya ya hi hey"
        val tokens = lexer.tokenize(string)
        val expected = listOf("hey", "ya", "hey", "hi", "hi", "ya", "ya", "hi", "hey").map { Token(it) }
        assertEquals(expected, tokens)
    }

    @Test
    fun `not match either on invalid sequence`() {
        val lexer = lexer {
            anyOf("hey", "ya", "hi") with { Token(it) }
            ignore(" ")
        }

        val string = "ho ho ho"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }
}