package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `And should` {
    @Test
    fun `match tokens matching both sides`() {
        val lexer = lexer {
            (('a' to 'z').oneOrMore() and "h") with { Token(it) }
            ignore(" ")
        }

        val string = "hey hi ho ho ho"
        val tokens = lexer.tokenize(string)
        val expected = tokensFrom(string)
        assertEquals(expected, tokens)
    }

    @Test
    fun `not match a token not matching left side`() {
        val lexer = lexer {
            ('a' to 'z').oneOrMore() and "0" with { Token(it) }
            ignore(" ")
        }

        val string = "0az"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }


    @Test
    fun `not match a token not matching right side`() {
        val lexer = lexer {
            ('a' to 'z').oneOrMore() and "e" with { Token(it) }
            ignore(" ")
        }

        val string = "hey"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }
}
