package kobold.matchers

import kobold.Accepted
import kobold.NoRemainingTokens
import kobold.Rejected
import kobold.UnexpectedToken
import kobold.parser.dsl.support.tokens
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class `Not should` {
    private val grammar = Not(TerminalByContent(Token('a')))

    @Test
    fun `accept an otherwise rejected match`() {
        val result = grammar.match(tokens("b"))
        assertIs<Accepted>(result)
    }

    @Test
    fun `accept an empty sequence`() {
        val result = grammar.match(tokens(""))
        assertIs<Accepted>(result)
    }

    @Test
    fun `when combined with Any should accept an empty sequence`() {
        val grammar = Not(Any())
        val result = grammar.match(tokens(""))
        assertIs<Accepted>(result)
    }

    @Test
    fun `when combined with Any should reject a non empty sequence`() {
        val grammar = Not(Any())
        val result = grammar.match(tokens("a"))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        Assertions.assertEquals(Token("a"), reason.detected)
        Assertions.assertEquals(emptyList<Token>(), reason.expectedTokens.toList())
    }

    @Test
    fun `when combined with Empty should reject an empty sequence`() {
        val grammar = Not(Empty())
        val result = grammar.match(tokens(""))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<NoRemainingTokens>(reason)
    }

    @Test
    fun `reject an otherwise accepted match`() {
        val result = grammar.match(tokens("a"))
        assertIs<Rejected>(result)

        val reason = result.reason
        assertIs<UnexpectedToken>(reason)
        Assertions.assertEquals(Token("a"), reason.detected)
        Assertions.assertEquals(emptyList<Token>(), reason.expectedTokens.toList())
    }
}
