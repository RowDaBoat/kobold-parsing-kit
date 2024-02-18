package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokens
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

