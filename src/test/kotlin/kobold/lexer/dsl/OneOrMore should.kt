package kobold.lexer.dsl

import kobold.lexer.lexer
import kobold.matchers.Token
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class `OneOrMore should` {
    @Test
    fun `match some matching tokens`() {
        val case = "[one] [two] [three] [four] [hey] [ya] [hi] [ho]"
        val result = oneOrMore(case)
        assertEquals(tokensFrom(case), result)
    }

    @Test
    fun `match one matching token`() {
        val case = "[o]"
        val result = oneOrMore(case)
        assertEquals(tokensFrom(case), result)
    }

    @Test
    fun `not match no tokens`() {
        val case = "[] [] [four]"
        val result = oneOrMore(case)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `not match non matching tokens`() {
        val case = "[0909]"
        val result = oneOrMore(case)
        assertEquals(emptyList(), result)
    }

    private fun oneOrMore(case: String) =
        lexer {
            "[" then ('a' to 'z').oneOrMore() then "]" with { Token(it) }
            ignore(" ")
        }.tokenize(case)

}
