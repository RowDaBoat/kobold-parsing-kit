package kobold.parser.dsl

import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.parser.parser
import org.junit.jupiter.api.Test

class ThenShould {
    @Test
    fun acceptSomethingMatchingSequence() {
        val sequence = parser { "a" then "b" }
        assert(sequence.parse(tokens("ab")) is Accepted)
    }

    @Test
    fun rejectAnythingNotMatchingFirst() {
        val sequence = parser { "a" then "b" }
        assert(sequence.parse(tokens("b")) is Rejected)
    }

    @Test
    fun rejectAnythingNotMatchingSecond() {
        val sequence = parser { "a" then "b" }
        assert(sequence.parse(tokens("aa")) is Rejected)
    }
}

