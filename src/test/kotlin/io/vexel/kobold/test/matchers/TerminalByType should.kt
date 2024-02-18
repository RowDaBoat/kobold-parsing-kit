package io.vexel.kobold.test.matchers

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.TerminalByType
import io.vexel.kobold.matchers.match
import io.vexel.kobold.test.parser.dsl.support.tokens
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
        assertIs<io.vexel.kobold.Accepted>(result)
    }

    @Test
    fun `reject a non matching terminal`() {
        val grammar = TerminalByType(SomeToken::class.java)
        val result = grammar.match(listOf(OtherToken()))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.UnexpectedToken>(reason)
        assertEquals(OtherToken(), reason.detected)
        assertEquals(listOf(SomeToken::class.java), reason.expectedTokenTypes.toList())
    }

    @Test
    fun `reject an empty sequence`() {
        val grammar = TerminalByType(SomeToken::class.java)
        val result = grammar.match(tokens(""))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.NoRemainingTokens>(reason)
    }
}