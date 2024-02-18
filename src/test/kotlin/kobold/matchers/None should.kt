package kobold.matchers

import kobold.NoRemainingTokens
import kobold.Rejected
import kobold.UnexpectedToken
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class `None should` {
    @Test
    fun `reject any character`() {
        val grammar = None()
        val result = grammar.match(tokens("a"))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        assertEquals(Token("a"), reason.detected)
        assertEquals(emptyList<Token>(), reason.expectedTokens.toList())
    }

    @Test
    fun `reject an empty sequence`() {
        val grammar = None()
        val result = grammar.match(tokens(""))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<NoRemainingTokens>(reason)
    }
}

