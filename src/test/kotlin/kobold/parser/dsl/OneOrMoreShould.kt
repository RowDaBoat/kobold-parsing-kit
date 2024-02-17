package kobold.parser.dsl

import kobold.Accepted
import kobold.Rejected
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class OneOrMoreShould {
    @Test
    fun rejectZeroMatches() {
        val either = parser { "a".oneOrMore() }
        assertIs<Rejected>(either.parse(tokens("something")))
    }

    @Test
    fun acceptSomeMatching() {
        val either = parser { "a".oneOrMore() }
        assertIs<Accepted>(either.parse(tokens("aaaaaaaaa")))
    }

    @Test
    fun consumeAllOfTheMatch() {
        val either = parser { "a".oneOrMore() then "c" }
        assertIs<Accepted>(either.parse(tokens("aaaac")))
    }
}
