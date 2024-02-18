package kobold.matchers

import kobold.Accepted
import kobold.NoRemainingTokens
import kobold.Rejected
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class `Any should` {
    private val grammar = Any()

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
        assertIs<NoRemainingTokens>(reason)
    }
}
