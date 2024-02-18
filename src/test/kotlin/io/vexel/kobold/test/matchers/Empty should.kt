package io.vexel.kobold.test.matchers

import io.vexel.kobold.matchers.Empty
import io.vexel.kobold.matchers.match
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class `Empty should` {
    @Test
    fun `accept an empty token list`() {
        val grammar = Empty()
        val result = grammar.match(tokens(""))
        assertIs<io.vexel.kobold.Accepted>(result)
    }
}
