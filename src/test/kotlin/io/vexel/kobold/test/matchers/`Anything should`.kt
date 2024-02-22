package io.vexel.kobold.test.matchers

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.matchers.Anything
import io.vexel.kobold.matchers.match
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class `Anything should` {
    private val grammar = Anything()

    @Test
    fun `accept any token`() {
        val result = grammar.match(tokens("a"))
        assertIs<Accepted>(result)
    }

    @Test
    fun `reject an empty sequence`() {
        val result = grammar.match(tokens(""))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<io.vexel.kobold.NoRemainingTokens>(reason)
    }
}
