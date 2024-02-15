package kobold.parser.dsl

import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.parser.parser
import org.junit.jupiter.api.Test

class ZeroOrMoreShould {
    @Test
    fun acceptZeroMatching() {
        val either = parser { "a".zeroOrMore() }
        assert(either.parse(tokens("something")) is Accepted)
    }

    @Test
    fun acceptMoreMatching() {
        val either = parser { "a".zeroOrMore() }
        assert(either.parse(tokens("aaaaaaaaa")) is Accepted)
    }

    @Test
    fun consumeAllOfTheMatch() {
        val either = parser { "a".zeroOrMore() then "c"}
        assert(either.parse(tokens("aaaac")) is Accepted)
    }
}
