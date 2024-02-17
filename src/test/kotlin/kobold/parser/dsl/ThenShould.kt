package kobold.parser.dsl

import kobold.Accepted
import kobold.Rejected
import kobold.parser.dsl.support.tokens
import kobold.parser.parser
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class ThenShould {
    @Test
    fun acceptSomethingMatchingSequence() {
        val sequence = parser { "a" then "b" }
        assertIs<Accepted>(sequence.parse(tokens("ab")))
    }

    @Test
    fun rejectAnythingNotMatchingFirst() {
        val sequence = parser { "a" then "b" }
        assertIs<Rejected>(sequence.parse(tokens("b")))
    }

    @Test
    fun rejectAnythingNotMatchingSecond() {
        val sequence = parser { "a" then "b" }
        assertIs<Rejected>(sequence.parse(tokens("aa")))
    }
}

