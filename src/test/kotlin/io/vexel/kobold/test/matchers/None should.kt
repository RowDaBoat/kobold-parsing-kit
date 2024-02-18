package io.vexel.kobold.test.matchers

import io.vexel.kobold.Token
import io.vexel.kobold.matchers.None
import io.vexel.kobold.matchers.match
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class `None should` {
    @Test
    fun `reject any character`() {
        val grammar = None()
        val result = grammar.match(tokens("a"))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.UnexpectedToken>(reason)
        assertEquals(Token("a"), reason.detected)
        assertEquals(emptyList<Token>(), reason.expectedTokens.toList())
    }

    @Test
    fun `reject an empty sequence`() {
        val grammar = None()
        val result = grammar.match(tokens(""))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.NoRemainingTokens>(reason)
    }
}

