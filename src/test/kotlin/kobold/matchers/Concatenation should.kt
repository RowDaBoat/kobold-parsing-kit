package kobold.matchers

import kobold.Accepted
import kobold.NoRemainingTokens
import kobold.Rejected
import kobold.UnexpectedToken
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class `Concatenation should` {
    private val grammar = Concatenation(
        TerminalByContent(Token('(')),
        TerminalByContent(Token(')'))
    )

    @Test
    fun `accept a matching concatenation`() {
        val result = grammar.match(tokens("()"))
        assertIs<Accepted>(result)
    }

    @Test
    fun `reject a first term not matching`() {
        val result = grammar.match(tokens("))"))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        assertEquals(Token(')'), reason.detected)
        assertEquals(listOf(Token('(')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject a second term not matching`() {
        val result = grammar.match(tokens("(("))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        assertEquals(Token('('), reason.detected)
        assertEquals(listOf(Token(')')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject an empty not enough tokens for first term`() {
        val result = grammar.match(tokens(""))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<NoRemainingTokens>(reason)
    }

    @Test
    fun `reject an empty not enough tokens for second term`() {
        val result = grammar.match(tokens("("))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<NoRemainingTokens>(reason)
    }
}

