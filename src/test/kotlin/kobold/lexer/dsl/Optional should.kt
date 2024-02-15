package kobold.lexer.dsl

import kobold.lexer.lexer
import kobold.matchers.Token
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Optional should` {
    @Test
    fun `match tokens with and without optional expression`() {
        val lexer = lexer {
            "h" then ('a' to 'z').oneOrMore() then optional("?") with { Token(it) }
            ignore(" ")
        }

        val string = "hey? hey"
        val tokens = lexer.tokenize(string)
        val expected = tokensFrom(string)
        assertEquals(expected, tokens)
    }
}
