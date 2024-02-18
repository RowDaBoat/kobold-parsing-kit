package io.vexel.kobold.test.parser.dsl

import io.vexel.kobold.Accepted
import io.vexel.kobold.parser.parser
import io.vexel.kobold.test.parser.dsl.support.tokens
import org.junit.jupiter.api.Test
import kotlin.test.assertIs

class ZeroOrMoreShould {
    @Test
    fun acceptZeroMatching() {
        val either = parser { "a".zeroOrMore() then "s" }
        assertIs<Accepted>(either.parse(tokens("s")))
    }

    @Test
    fun acceptMoreMatching() {
        val either = parser { "a".zeroOrMore() }
        assertIs<Accepted>(either.parse(tokens("aaaaaaaaa")))
    }

    @Test
    fun consumeAllOfTheMatch() {
        val either = parser { "a".zeroOrMore() then "c" }
        assertIs<Accepted>(either.parse(tokens("aaaac")))
    }
}
