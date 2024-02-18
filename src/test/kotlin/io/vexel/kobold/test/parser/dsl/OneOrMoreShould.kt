package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.Rejected
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class OneOrMoreShould {
    @Test
    fun rejectZeroMatches() {
        val either = parser { "a".oneOrMore() }
        assertIs<Rejected>(either.parse(tokens("something")))
    }

    @Test
    fun acceptSomeMatching() {
        val either = parser { "a".oneOrMore() }
        assertIs<Accepted>(either.parse(tokens("aaaaaaaaa")))
    }

    @Test
    fun consumeAllOfTheMatch() {
        val either = parser { "a".oneOrMore() then "c" }
        assertIs<Accepted>(either.parse(tokens("aaaac")))
    }
}
