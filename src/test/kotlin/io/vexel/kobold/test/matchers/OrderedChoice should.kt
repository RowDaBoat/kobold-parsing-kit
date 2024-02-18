package io.vexel.kobold.test.matchers

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Concatenation
import io.vexel.kobold.matchers.OrderedChoice
import io.vexel.kobold.matchers.TerminalByContent
import io.vexel.kobold.matchers.match
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class `OrderedChoice should` {
    @Test
    fun `accept a an ordered choice matching first`() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')))
        val result = grammar.match(tokens("a"))
        assertIs<io.vexel.kobold.Accepted>(result)
    }

    @Test
    fun `accept a an ordered choice matching second`() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')))
        val result = grammar.match(tokens("b"))
        assertIs<io.vexel.kobold.Accepted>(result)
    }

    @Test
    fun `reject an ordered choice matching neither`() {
        val grammar = OrderedChoice(TerminalByContent(Token('a')), TerminalByContent(Token('b')));
        val result = grammar.match(tokens("c"))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.UnexpectedToken>(reason)
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
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.NoRemainingTokens>(reason)
    }

    @Test
    fun `reject short sequence for first term and non matching for second term`() {
        val a = TerminalByContent(Token('a'))
        val b = TerminalByContent(Token('b'))

        val grammar = OrderedChoice(Concatenation(a, a), b)

        val result = grammar.match(tokens("a"))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.UnexpectedToken>(reason)
        assertEquals(Token('a'), reason.detected)
        assertEquals(listOf(Token('b')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject non matching sequence for first term and short for second term`() {
        val a = TerminalByContent(Token('a'))
        val b = TerminalByContent(Token('b'))

        val grammar = OrderedChoice(a, Concatenation(b, b))

        val result = grammar.match(tokens("b"))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.UnexpectedToken>(reason)
        assertEquals(Token('b'), reason.detected)
        assertEquals(listOf(Token('a')), reason.expectedTokens.toList())
    }
}