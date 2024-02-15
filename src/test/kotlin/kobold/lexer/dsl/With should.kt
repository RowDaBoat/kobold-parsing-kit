package kobold.lexer.dsl

import kobold.lexer.lexer
import kobold.matchers.Token
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `With should` {
    @Test
    fun `produce tokens when matching a string`() {
        val lexer = lexer { "a" with { Token(it) } }

        val string = "aaaaaaaaaaaaaaaaaaaaaaaa"
        val tokens = lexer.tokenize(string)
        val expected = string.map { Token(it) }.toList()
        assertEquals(expected, tokens)
    }

    @Test
    fun `produce tokens when matching a matcher`() {
        val lexer = lexer { "a" or "b" with { Token(it) } }

        val string = "aaababaabbabaababbaabbbababababaabababaaaa"
        val tokens = lexer.tokenize(string)
        val expected = string.map { Token(it) }.toList()
        assertEquals(expected, tokens)
    }

    @Test
    fun `not produce tokens when not matching`() {
        val lexer = lexer { "a" or "b" with { Token(it) } }

        val string = "cccccccccccccccccccccccc"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }

    @Test
    fun `produce different tokens for each rule`() {
        val lexer = lexer {
            "a" with { Token(it) }
            "b" with { Token(it) }
        }

        val string = "aaababbabaababbabbabababababababbabbabababbabab"
        val tokens = lexer.tokenize(string)
        val expected = string.map { Token(it) }.toList()
        assertEquals(expected, tokens)
    }
}