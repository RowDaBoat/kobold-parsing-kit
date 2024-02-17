package kobold.parser.dsl

import kobold.Accepted
import kobold.Rejected
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class SequenceShould {
    @Test
    fun acceptSomethingMatchingSequence() {
        val sequence = parser { sequence("a", "b", "c", "d") }
        assertIs<Accepted>(sequence.parse(tokens("abcd")))
    }

    @Test
    fun rejectAnythingNotMatchingFirst() {
        val sequence = parser { sequence("a", "b", "c", "d") }
        assertIs<Rejected>(sequence.parse(tokens("b")))
    }

    @Test
    fun rejectAnythingNotMatchingOther() {
        val sequence = parser { sequence("a", "b", "c", "d") }
        assertIs<Rejected>(sequence.parse(tokens("abc")))
    }
}

