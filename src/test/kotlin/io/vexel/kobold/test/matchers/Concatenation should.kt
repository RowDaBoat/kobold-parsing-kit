package io.vexel.kobold.test.matchers

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.Concatenation
import io.vexel.kobold.matchers.TerminalByContent
import io.vexel.kobold.matchers.match
import io.vexel.kobold.test.parser.dsl.support.tokens
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
        assertIs<io.vexel.kobold.Accepted>(result)
    }

    @Test
    fun `reject a first term not matching`() {
        val result = grammar.match(tokens("))"))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.UnexpectedToken>(reason)
        assertEquals(Token(')'), reason.detected)
        assertEquals(listOf(Token('(')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject a second term not matching`() {
        val result = grammar.match(tokens("(("))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.UnexpectedToken>(reason)
        assertEquals(Token('('), reason.detected)
        assertEquals(listOf(Token(')')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject an empty not enough tokens for first term`() {
        val result = grammar.match(tokens(""))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.NoRemainingTokens>(reason)
    }

    @Test
    fun `reject an empty not enough tokens for second term`() {
        val result = grammar.match(tokens("("))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.NoRemainingTokens>(reason)
    }
}

