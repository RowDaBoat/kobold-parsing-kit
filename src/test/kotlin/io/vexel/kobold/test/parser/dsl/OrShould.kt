package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class OrShould {
    @Test
    fun acceptAnythingMatchingEither() {
        val either = parser { "a" or "b" }
        assertIs<Accepted>(either.parse(tokens("a")))
        assertIs<Accepted>(either.parse(tokens("b")))
    }

    @Test
    fun rejectAnythingNotMatchingEither() {
        val either = parser { "a" or "b" }
        assertIs<Rejected>(either.parse(tokens("c")))
    }
}

