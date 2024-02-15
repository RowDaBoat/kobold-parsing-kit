package kobold.parser.dsl

import kobold.parser.dsl.support.tokenMatchers
import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.parser.parser
import org.junit.jupiter.api.Test

class NotShould {
    @Test
    fun acceptAnythingNotMatchingSequence() {
        val not = parser { not("a") }
        assert(not.parse(tokens("vrienb")) is Accepted)
    }

    @Test
    fun rejectAnythingMatchingSequence() {
        val not = parser { not("a") }
        assert(not.parse(tokens("a")) is Rejected)
        assert(not.parse(tokens("aa")) is Rejected)
        assert(not.parse(tokens("b")) is Accepted)
    }

    @Test
    fun rejectMatchingExactSequence() {
        val not = parser { not(sequence(tokenMatchers("aaa"))) }
        assert(not.parse(tokens("aab")) is Accepted)
        assert(not.parse(tokens("aac")) is Accepted)
        assert(not.parse(tokens("aaa")) is Rejected)
    }
}
