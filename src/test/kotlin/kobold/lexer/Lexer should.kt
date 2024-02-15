package kobold.lexer

import kobold.tokens.Number
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `Lexer should` {
    @Test
    fun `match simple token definitions` () {
        val lexer = lexer {
            val alpha = ('a' to 'z') or ('A' to 'Z')
            val numeric = '0' to '9'

            alpha then (alpha or numeric).zeroOrMore() with { Label(it) }
            numeric.oneOrMore() with { Number(it) }
            ignore(" " or "!" or "," or ",")

        }

        val tokens = lexer.tokenize("H3llo Kobold Lexer! 9999")
        val expected = listOf(Label("H3llo"), Label("Kobold"), Label("Lexer"), Number("9999"))
        assertEquals(expected, tokens)

    }
}

