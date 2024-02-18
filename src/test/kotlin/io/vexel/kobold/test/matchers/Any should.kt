package io.vexel.kobold.test.matchers

import io.vexel.kobold.matchers.Any
import io.vexel.kobold.matchers.match
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class `Any should` {
    private val grammar = Any()

    @Test
    fun `accept any token`() {
        val result = grammar.match(tokens("a"))
        assertIs<io.vexel.kobold.Accepted>(result)
    }

    @Test
    fun `reject an empty sequence`() {
        val result = grammar.match(tokens(""))
        assertIs<io.vexel.kobold.Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.NoRemainingTokens>(reason)
    }
}
