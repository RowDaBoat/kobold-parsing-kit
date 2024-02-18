package io.vexel.kobold.test.matchers

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.TerminalByContent
import io.vexel.kobold.matchers.match
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class `TerminalByContent should` {
    @Test
    fun `accept a matching terminal`() {
        val grammar = TerminalByContent(Token('('))
        val result = grammar.match(tokens("("))
        assertIs<io.vexel.kobold.Accepted>(result)
    }

    @Test
    fun `reject a non matching terminal`() {
        val grammar = TerminalByContent(Token('('))
        val result = grammar.match(tokens(")"))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.UnexpectedToken>(reason)
        assertEquals(Token(')'), reason.detected)
        assertEquals(listOf(Token('(')), reason.expectedTokens.toList())
    }

    @Test
    fun `reject an empty sequence`() {
        val grammar = TerminalByContent(Token('a'))
        val result = grammar.match(tokens(""))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.NoRemainingTokens>(reason)
    }
}
