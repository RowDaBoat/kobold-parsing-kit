package kobold.matchers

import kobold.Accepted
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class `Empty should` {
    @Test
    fun `accept an empty token list`() {
        val grammar = Empty()
        val result = grammar.match(tokens(""))
        assertIs<Accepted>(result)
    }
}
