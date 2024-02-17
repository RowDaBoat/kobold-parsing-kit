package kobold.parser.dsl

import kobold.Accepted
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class ZeroOrMoreShould {
    @Test
    fun acceptZeroMatching() {
        val either = parser { "a".zeroOrMore() then "s" }
        assertIs<Accepted>(either.parse(tokens("s")))
    }

    @Test
    fun acceptMoreMatching() {
        val either = parser { "a".zeroOrMore() }
        assertIs<Accepted>(either.parse(tokens("aaaaaaaaa")))
    }

    @Test
    fun consumeAllOfTheMatch() {
        val either = parser { "a".zeroOrMore() then "c" }
        assertIs<Accepted>(either.parse(tokens("aaaac")))
    }
}
