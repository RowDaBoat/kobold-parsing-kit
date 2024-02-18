package kobold.matchers

import kobold.Accepted
import kobold.NoRemainingTokens
import kobold.Rejected
import kobold.UnexpectedToken
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class `TerminalByContent should` {
    @Test
    fun `accept a matching terminal`() {
        val grammar = TerminalByContent(Token('('))
        val result = grammar.match(tokens("("))
        assertIs<Accepted>(result)
    }

    @Test
    fun `reject a non matching terminal`() {
        val grammar = TerminalByContent(Token('('))
        val result = grammar.match(tokens(")"))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        assertEquals(Token(')'), reason.detected)
        assertEquals(listOf(Token('(')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject an empty sequence`() {
        val grammar = TerminalByContent(Token('a'))
        val result = grammar.match(tokens(""))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<NoRemainingTokens>(reason)
    }
}
