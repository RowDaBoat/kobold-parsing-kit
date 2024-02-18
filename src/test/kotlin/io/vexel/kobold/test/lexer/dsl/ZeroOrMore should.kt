package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `ZeroOrMore should` {
    @Test
    fun `match tokens with some characters`() {
        val lexer = lexer {
            "[" then "h" then ('a' to 'z').zeroOrMore() then "]" with { Token(it) }
            ignore(" ")
        }

        val string = "[hey] [hi]  [ho] [h]"
        val tokens = lexer.tokenize(string)
        val expected = tokensFrom(string)
        assertEquals(expected, tokens)
    }

    @Test
    fun `match tokens with no characters`() {
        val lexer = lexer {
            "[" then ('a' to 'z').zeroOrMore() then "]" with { Token(it) }
            ignore(" ")
        }

        val string = "[] [hey] [hi] [] [] [ho] [ho] [ho]"
        val tokens = lexer.tokenize(string)
        val expected = tokensFrom(string)
        assertEquals(expected, tokens)
    }
}
