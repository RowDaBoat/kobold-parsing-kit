package kobold.parser.dsl

import kobold.parser.dsl.support.tokens
import kobold.matchers.Accepted
import kobold.matchers.Rejected
import kobold.parser.parser
import org.junit.jupiter.api.Test

class SequenceShould {
    @Test
    fun acceptSomethingMatchingSequence() {
        val sequence = parser { sequence("a", "b", "c", "d") }
        assert(sequence.parse(tokens("abcd")) is Accepted)
    }

    @Test
    fun rejectAnythingNotMatchingFirst() {
        val sequence = parser { sequence("a", "b", "c", "d") }
        assert(sequence.parse(tokens("b")) is Rejected)
    }

    @Test
    fun rejectAnythingNotMatchingOther() {
        val sequence = parser { sequence("a", "b", "c", "d") }
        assert(sequence.parse(tokens("abc")) is Rejected)
    }
}

