package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokens
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

