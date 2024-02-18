package kobold.matchers

import kobold.Accepted
import kobold.NoRemainingTokens
import kobold.Rejected
import kobold.UnexpectedToken
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class `OrderedChoice should` {
    @Test
    fun `accept a an ordered choice matching first`() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')))
        val result = grammar.match(tokens("a"))
        assertIs<Accepted>(result)
    }

    @Test
    fun `accept a an ordered choice matching second`() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')))
        val result = grammar.match(tokens("b"))
        assertIs<Accepted>(result)
    }

    @Test
    fun `reject an ordered choice matching neither`() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')));
        val result = grammar.match(tokens("c"))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        assertEquals(Token('c'), reason.detected)
        assertEquals(listOf(Token('a'), Token('b')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject empty sequence on both terms`() {
        val grammar = OrderedChoice(
            TerminalByContent(Token('a')),
            TerminalByContent(Token('b'))
        )
        val result = grammar.match(tokens(""))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<NoRemainingTokens>(reason)
    }

    @Test
    fun `reject short sequence for first term and non matching for second term`() {
        val a = TerminalByContent(Token('a'))
        val b = TerminalByContent(Token('b'))

        val grammar = OrderedChoice(Concatenation(a, a), b)

        val result = grammar.match(tokens("a"))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        assertEquals(Token('a'), reason.detected)
        assertEquals(listOf(Token('b')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject non matching sequence for first term and short for second term`() {
        val a = TerminalByContent(Token('a'))
        val b = TerminalByContent(Token('b'))

        val grammar = OrderedChoice(a, Concatenation(b, b))

        val result = grammar.match(tokens("b"))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        assertEquals(Token('b'), reason.detected)
        assertEquals(listOf(Token('a')), reason.expectedTokens.toList())
    }
}