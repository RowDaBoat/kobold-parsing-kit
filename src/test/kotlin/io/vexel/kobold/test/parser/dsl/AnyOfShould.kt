package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class AnyOfShould {
    @Test
    fun acceptAnythingMatchingOptions() {
        val anyOf = parser { anyOf("a", "b", "c", "d") }
        assertIs<Accepted>(anyOf.parse(tokens("a")))
        assertIs<Accepted>(anyOf.parse(tokens("b")))
        assertIs<Accepted>(anyOf.parse(tokens("c")))
        assertIs<Accepted>(anyOf.parse(tokens("d")))
    }

    @Test
    fun rejectEverythingNotMatchingOptions() {
        val anyOf = parser { anyOf("a", "b", "c", "d") }
        assertIs<Rejected>(anyOf.parse(tokens("e")))
    }
}
