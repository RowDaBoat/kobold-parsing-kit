package kobold.lexer.dsl

import kobold.lexer.lexer
import kobold.matchers.Token
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Then should` {
    @Test
    fun `match this then that`() {
        val lexer = lexer {
            "hey" then "ya" with { Token(it) }
            ignore(" ")
        }

        val string = "heyya heyya heyya"
        val tokens = lexer.tokenize(string)
        val expected = tokensFrom(string)
        assertEquals(expected, tokens)
    }

    @Test
    fun `not match something not matching in left matcher`() {
        val lexer = lexer { "hey" then "ya" with { Token(it) } }

        val string = "yaya"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }

    @Test
    fun `not match something not matching in right matcher`() {
        val lexer = lexer { "hey" then "ya" with { Token(it) } }

        val string = "heyhey"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }
}
