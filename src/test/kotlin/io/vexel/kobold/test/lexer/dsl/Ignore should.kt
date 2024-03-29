package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Ignore should` {
    @Test
    fun `not emit tokens matching ignored string`() {
        val lexer = lexer {
            "a" with { Token(it) }
            ignore(" ")
        }

        val tokens = lexer.tokenize("a a   a  a")
        val expected = "aaaa".map { Token(it) }.toList()
        assertEquals(expected, tokens)
    }

    @Test
    fun `not emit tokens matching ignored matcher`() {
        val lexer = lexer {
            "a" with { Token(it) }
            ignore(" " or "-")
        }

        val tokens = lexer.tokenize("a-a  -- - - a--a")
        val expected = "aaaa".map { Token(it) }.toList()
        assertEquals(expected, tokens)
    }
}
