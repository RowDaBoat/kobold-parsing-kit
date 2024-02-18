package kobold.matchers

import kobold.Accepted
import kobold.NoRemainingTokens
import kobold.Rejected
import kobold.UnexpectedToken
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class `TerminalByType should` {
    class SomeToken : Token('+')
    class OtherToken : Token('+')

    @Test
    fun `accept a matching terminal`() {
        val grammar = TerminalByType(SomeToken::class.java)
        val result = grammar.match(listOf(SomeToken()))
        assertIs<Accepted>(result)
    }

    @Test
    fun `reject a non matching terminal`() {
        val grammar = TerminalByType(SomeToken::class.java)
        val result = grammar.match(listOf(OtherToken()))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        assertEquals(OtherToken(), reason.detected)
        assertEquals(listOf(SomeToken::class.java), reason.expectedTokenTypes.toList())
    }

    @Test
    fun `reject an empty sequence`() {
        val grammar = TerminalByType(SomeToken::class.java)
        val result = grammar.match(tokens(""))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<NoRemainingTokens>(reason)
    }
}