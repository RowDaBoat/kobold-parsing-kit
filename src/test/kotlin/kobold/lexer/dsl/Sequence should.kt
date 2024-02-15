package kobold.lexer.dsl

import kobold.lexer.lexer
import kobold.matchers.Token
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Sequence should` {
    @Test
    fun `match in sequence`() {
        val lexer = lexer {
            sequence("hey", "ya", "hi") with { Token(it) }
            ignore(" ")
        }

        val string = "heyyahi heyyahiheyyahi"
        val tokens = lexer.tokenize(string)
        val expected = listOf("heyyahi", "heyyahi", "heyyahi").map { Token(it) }
        assertEquals(expected, tokens)
    }

    @Test
    fun `not match not in sequence`() {
        val lexer = lexer {
            sequence("hey", "ya", "hi") with { Token(it) }
            ignore(" ")
        }

        val string = "heyyahhi"
        val tokens = lexer.tokenize(string)
        assertEquals(emptyList(), tokens)
    }
}