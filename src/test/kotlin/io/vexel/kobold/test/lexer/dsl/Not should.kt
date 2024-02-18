package io.vexel.kobold.test.lexer.dsl

import io.vexel.kobold.Token
import io.vexel.kobold.lexer.lexer
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Not should` {
    @Test
    fun `match tokens not matching expression`() {
        val lexer = lexer {
            ('a' to 'z').oneOrMore() and not("h") with { Token(it) }
            ignore(" ")
        }

        val string = "oh this is a match"
        val tokens = lexer.tokenize(string)
        val expected = tokensFrom(string)
        assertEquals(expected, tokens)
    }

    @Test
    fun `not match a token not matching expression`() {
        val lexer = lexer { not("h") with { Token(it) } }
        val string = "hey"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }
}

