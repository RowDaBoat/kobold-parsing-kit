package kobold.lexer.dsl

import kobold.lexer.lexer
import kobold.matchers.Token
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