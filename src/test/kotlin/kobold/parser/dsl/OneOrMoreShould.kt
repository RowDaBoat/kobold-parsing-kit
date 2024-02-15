package kobold.parser.dsl

import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.parser.parser
import org.junit.jupiter.api.Test

class OneOrMoreShould {
    @Test
    fun rejectZeroMatches() {
        val either = parser { "a".oneOrMore() }
        assert(either.parse(tokens("something")) is Rejected)
    }

    @Test
    fun acceptSomeMatching() {
        val either = parser { "a".oneOrMore() }
        assert(either.parse(tokens("aaaaaaaaa")) is Accepted)
    }

    @Test
    fun consumeAllOfTheMatch() {
        val either = parser { "a".oneOrMore() then "c" }
        assert(either.parse(tokens("aaaac")) is Accepted)
    }
}
